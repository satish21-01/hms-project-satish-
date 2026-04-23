package com.hms.doctor_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorRequestDTO {

    @NotBlank(message = "Doctor name is required")
    private String name;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Email is required")
    private String email;

    @Min(value = 0, message = "Experience must be 0 or greater")
    private Integer experience;


    private boolean available;

}
