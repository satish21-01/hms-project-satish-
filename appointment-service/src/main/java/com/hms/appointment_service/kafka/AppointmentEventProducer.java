package com.hms.appointment_service.kafka;

import com.hms.appointment_service.event.AppointmentBookedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentEventProducer {

    private static final String TOPIC = "appointment-booked";

    private final KafkaTemplate<String, AppointmentBookedEvent> kafkaTemplate;

    public AppointmentEventProducer(
            KafkaTemplate<String, AppointmentBookedEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishAppointmentBookedEvent(AppointmentBookedEvent event) {
        kafkaTemplate.send(TOPIC, event);
        System.out.println("✅ Kafka event sent: " + event);
    }
}
