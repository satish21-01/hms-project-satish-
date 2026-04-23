package com.hms.billing_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long appointmentId;
    private Long patientId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    private LocalDateTime createdAt;
}
