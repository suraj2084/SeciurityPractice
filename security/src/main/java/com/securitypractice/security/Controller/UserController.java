package com.securitypractice.security.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securitypractice.security.Model.User;
import com.securitypractice.security.Payloads.UserDto;
import com.securitypractice.security.Payloads.Response.ApiResponse;
import com.securitypractice.security.SecurityConfig.Jwts.JwtsUtils;
import com.securitypractice.security.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtsUtils jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> CreateUser(@RequestBody UserDto userDto) {

        ApiResponse user = userService.createUser(userDto);
        System.out.println(user.toString());
        return new ResponseEntity<ApiResponse>(user, HttpStatus.CREATED);

    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin access granted.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER','ADMIN')")
    public String userAccess() {
        System.out.println("THIS HIT.");
        return "User access granted.WELCOME USER!";
    }

    // POST endpoint to authenticate and generate JWT token
    @PostMapping("/auth/login")
    public String login(@RequestBody UserDto userDto) {
        User user = userService.findByUserNameAndPassword(userDto);

        if (user != null) {
            return jwtUtil.CreateToken(user.getUserName());
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }

}
