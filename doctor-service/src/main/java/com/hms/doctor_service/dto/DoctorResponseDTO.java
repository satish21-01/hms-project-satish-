package com.hms.doctor_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoctorResponseDTO {

    private Long id;
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private boolean available;   // 🔥 VERY IMPORTANT
    private Integer experience;
    private LocalDateTime createdAt;
}
