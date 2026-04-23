package com.hms.patient_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientPatchRequestDTO {

    @Size(min = 2, max = 100, message = "Name must be 2–100 characters")
    private String name;

    @Min(value = 1, message = "Age must be greater than 0")
    private Integer age;

    @Size(min = 10, max = 15, message = "Phone number must be 10–15 digits")
    private String phone;

    private String gender;
    private String address;
}
