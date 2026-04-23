package com.hms.appointment_service.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/appointments/test")
    public String test(Authentication authentication) {
        return "User: " + authentication.getName();
    }
}