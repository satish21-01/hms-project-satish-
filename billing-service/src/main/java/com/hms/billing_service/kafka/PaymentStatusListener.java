package com.hms.billing_service.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentStatusListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "payment-status-updated",
            groupId = "notification-group"
    )
    public void listen(String message) {

        try {
            System.out.println("💳 Payment status event received:");
            System.out.println(message);

            JsonNode json = objectMapper.readTree(message);

            String status = json.get("status").asText();
            Long patientId = json.get("patientId").asLong();
            Double amount = json.get("amount").asDouble();

            if ("PAID".equals(status)) {
                sendPaymentSuccess(patientId, amount);
            } else {
                sendPaymentFailure(patientId);
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to process payment status notification");
            throw new RuntimeException(e);
        }
    }

    private void sendPaymentSuccess(Long patientId, Double amount) {
        System.out.println("📧 Payment SUCCESS notification sent");
        System.out.println("PatientId=" + patientId + ", Amount=" + amount);
    }

    private void sendPaymentFailure(Long patientId) {
        System.out.println("📧 Payment FAILURE notification sent");
        System.out.println("PatientId=" + patientId);
    }
}
