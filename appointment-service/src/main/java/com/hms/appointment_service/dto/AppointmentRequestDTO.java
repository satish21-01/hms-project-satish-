package com.hms.appointment_service.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class AppointmentRequestDTO {

    @NotNull(message = "DoctorId is required")
    private Long doctorId;

    @NotNull(message = "PatientId is required")
    private Long patientId;

    @NotNull(message = "Appointment date is required")
    private LocalDate appointmentDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    // getters & setters
}