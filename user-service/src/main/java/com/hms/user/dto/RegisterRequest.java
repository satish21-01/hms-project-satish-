package com.hms.user.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role; // patient,doctor,admin
}