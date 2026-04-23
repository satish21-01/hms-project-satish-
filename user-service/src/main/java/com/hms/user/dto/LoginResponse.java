package com.hms.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String email;
    private Set<String> roles;
}