package com.hms.billing_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentStatusEvent {

    private Long invoiceId;
    private Long appointmentId;
    private Long patientId;
    private Double amount;
    private String status; // PAID / FAILED
    private LocalDateTime updatedAt;
}
