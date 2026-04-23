package com.hms.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/secure")
    public String secure(){
        return "JWT WORKING!";
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public String patient() {
        return "PATIENT ACCESS OK";
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public String doctor() {
        return "DOCTOR ACCESS OK";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "ADMIN ACCESS OK";
    }
}

