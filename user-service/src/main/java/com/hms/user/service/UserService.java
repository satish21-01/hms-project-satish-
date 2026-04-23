package com.hms.user.service;

import com.hms.user.dto.RegisterRequest;
import com.hms.user.dto.UserResponse;
import com.hms.user.entity.Role;
import com.hms.user.entity.User;
import com.hms.user.repository.RoleRepository;
import com.hms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {

        // 1️⃣ Check email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // 2️⃣ Fetch role from DB
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 3️⃣ Create user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));

        // 4️⃣ Save user
        User savedUser = userRepository.save(user);

        // 5️⃣ Convert roles to String
        Set<String> roles = savedUser.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        // 6️⃣ Return DTO (NO PASSWORD 🔐)
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                roles
        );
    }
}