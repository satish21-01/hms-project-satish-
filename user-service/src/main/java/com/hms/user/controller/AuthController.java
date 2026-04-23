package com.hms.user.controller;

import com.hms.user.dto.LoginRequest;
import com.hms.user.dto.LoginResponse;
import com.hms.user.dto.RegisterRequest;
import com.hms.user.dto.UserResponse;
import com.hms.user.service.AuthService;
import com.hms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}