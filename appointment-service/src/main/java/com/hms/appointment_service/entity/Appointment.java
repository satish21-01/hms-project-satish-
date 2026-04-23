package com.hms.appointment_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Reference from Hospital Service
    @Column(nullable = false)
    private Long doctorId;

    // 🔗 Reference from User / Patient
    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = AppointmentStatus.BOOKED;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
