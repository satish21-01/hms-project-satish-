package com.hms.billing_service.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hms.billing_service.entity.Invoice;
import com.hms.billing_service.entity.InvoiceStatus;
import com.hms.billing_service.repository.InvoiceRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentBookedConsumer {

    private final InvoiceRepository invoiceRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AppointmentBookedConsumer(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @KafkaListener(
            topics = "appointment-booked",
            groupId = "billing-group"
    )
    public void consume(String message) {

        try {
            System.out.println("📩 Billing Service received Kafka message:");
            System.out.println(message);

            JsonNode json = objectMapper.readTree(message);

            Long appointmentId = json.get("appointmentId").asLong();
            Long patientId = json.get("patientId").asLong();

            // 💰 simple amount logic (temporary)
            double amount = 500.0;

            Invoice invoice = Invoice.builder()
                    .appointmentId(appointmentId)
                    .patientId(patientId)
                    .amount(amount)
                    .status(InvoiceStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

            invoiceRepository.save(invoice);

            System.out.println("🧾 Invoice created with status PENDING");

        } catch (Exception e) {
            System.out.println("❌ Billing failed");
            throw new RuntimeException(e);
        }
    }
}
