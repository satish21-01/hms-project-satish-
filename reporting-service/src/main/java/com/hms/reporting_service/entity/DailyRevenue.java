package com.hms.reporting_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "daily_revenue",
        uniqueConstraints = @UniqueConstraint(columnNames = {"revenueDate"})
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DailyRevenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate revenueDate;

    private Double totalAmount;
    private Long paymentsCount;
}
