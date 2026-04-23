package com.hms.user.service;

import com.hms.user.dto.LoginRequest;
import com.hms.user.dto.LoginResponse;
import com.hms.user.entity.Role;
import com.hms.user.entity.User;
import com.hms.user.exception.AuthenticationException;
import com.hms.user.exception.ResourceNotFoundException;
import com.hms.user.repository.UserRepository;
import com.hms.user.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        // 1️⃣ User check
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        // 2️⃣ Password check
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        // 3️⃣ Roles extract
        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        // 4️⃣ JWT generate
        String token = jwtUtil.generateToken(user.getEmail(), roles);

        // 5️⃣ Return DTO
        return new LoginResponse(token, user.getEmail(), roles);
    }
}