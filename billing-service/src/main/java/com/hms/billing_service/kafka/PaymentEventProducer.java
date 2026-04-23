package com.hms.billing_service.kafka;

import com.hms.billing_service.dto.PaymentStatusEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducer {

    private static final String TOPIC = "payment-status-updated";

    private final KafkaTemplate<String, PaymentStatusEvent> kafkaTemplate;

    public PaymentEventProducer(
            KafkaTemplate<String, PaymentStatusEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(PaymentStatusEvent event) {
        kafkaTemplate.send(TOPIC, event);
        System.out.println("💳 Kafka Payment Event sent: " + event);
    }
}
