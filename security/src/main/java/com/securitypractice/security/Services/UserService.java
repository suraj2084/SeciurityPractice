package com.securitypractice.security.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.securitypractice.security.Model.User;
import com.securitypractice.security.Payloads.UserDto;
import com.securitypractice.security.Payloads.Response.ApiResponse;

@Service
public interface UserService {
    ApiResponse createUser(UserDto userDto);

    ApiResponse updateUser(Integer user_id, UserDto userDto);

    ApiResponse deleteUser(Integer user_id);

    UserDto getUser(Integer user_id);

    List<UserDto> getAllUser();

    User findByUserNameAndPassword(UserDto userDto);

}