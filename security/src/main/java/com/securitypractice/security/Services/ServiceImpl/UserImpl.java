package com.securitypractice.security.Services.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.securitypractice.security.Exceptions.ResourceNotFoundException;
import com.securitypractice.security.Model.User;
import com.securitypractice.security.Payloads.UserDto;
import com.securitypractice.security.Payloads.Response.ApiResponse;
import com.securitypractice.security.Repository.UserRepo;
import com.securitypractice.security.Services.UserService;

public class UserImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return new ApiResponse("User Created Sucessfully", true);
    }

    @Override
    public ApiResponse updateUser(Integer user_id, UserDto userDto) {
        User user = userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        if (userDto.getUserName() != null) {
            user.setUserName(userDto.getUserName());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        userRepo.save(user);
        return new ApiResponse("User Updated Sucessfully", true);
    }

    @Override
    public ApiResponse deleteUser(Integer user_id) {
        User user = userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        userRepo.delete(user);
        return new ApiResponse("User Deleted Sucessfully", true);
    }

    @Override
    public UserDto getUser(Integer user_id) {
        User user = userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> list = userRepo.findAll();
        List<UserDto> userDtos = list.stream().map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public User findByUserNameAndPassword(UserDto userDto) {
        User user = userRepo.findByUserName(userDto.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", 1));
        if (user != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }

}
