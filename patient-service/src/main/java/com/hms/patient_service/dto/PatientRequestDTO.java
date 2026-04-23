package com.hms.patient_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequestDTO {


    @NotNull(message = "UserId is required")   // ✅ ADD
    private Long userId;

    @NotBlank(message = "Patient name is required")
    @Size(min = 2, max = 100, message = "Name must be 2–100 characters")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be greater than 0")
    private Integer age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number must be 10–15 digits")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;
}