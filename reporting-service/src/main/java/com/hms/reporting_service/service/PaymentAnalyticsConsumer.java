package com.hms.reporting_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hms.reporting_service.entity.DailyRevenue;
import com.hms.reporting_service.repository.DailyRevenueRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentAnalyticsConsumer {

    private final DailyRevenueRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    public PaymentAnalyticsConsumer(DailyRevenueRepository repo) {
        this.repo = repo;
    }

    @KafkaListener(
            topics = "payment-status-updated",
            groupId = "reporting-group"
    )
    public void consume(String message) {
        try {
            JsonNode json = mapper.readTree(message);

            String status = json.get("status").asText();
            if (!"PAID".equals(status)) {
                return; // only PAID contributes to revenue
            }

            double amount = json.get("amount").asDouble();
            LocalDate today = LocalDate.now();

            DailyRevenue daily = repo.findByRevenueDate(today)
                    .orElseGet(() -> DailyRevenue.builder()
                            .revenueDate(today)
                            .totalAmount(0.0)
                            .paymentsCount(0L)
                            .build());

            daily.setTotalAmount(daily.getTotalAmount() + amount);
            daily.setPaymentsCount(daily.getPaymentsCount() + 1);

            repo.save(daily);

            System.out.println("📊 Revenue updated for " + today);

        } catch (Exception e) {
            System.out.println("❌ Reporting failed");
            throw new RuntimeException(e);
        }
    }
}
