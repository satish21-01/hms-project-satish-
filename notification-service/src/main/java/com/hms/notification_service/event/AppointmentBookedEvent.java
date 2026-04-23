package com.hms.notification_service.event;



import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentBookedEvent {

    private Long appointmentId;
    private Long doctorId;
    private Long patientId;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
}
