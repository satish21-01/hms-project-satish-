package com.hms.patient_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PatientResponseDTO {

    private Long id;
    private Long userId;   // ✅ ADD THIS

    private String name;
    private Integer age;
    private String gender;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
}