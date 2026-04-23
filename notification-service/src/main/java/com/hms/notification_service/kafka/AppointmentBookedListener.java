package com.hms.notification_service.kafka;


import com.hms.notification_service.event.AppointmentBookedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AppointmentBookedListener {
    @KafkaListener(
            topics = "appointment-booked",
            groupId = "notification-group"
    )
    public void listen(String message) {
        System.out.println("📩 RAW Kafka message received:");
        System.out.println(message);
        // Fake notification logic
        boolean notificationSent = sendNotification(message);

        if (!notificationSent) {
            throw new RuntimeException("Notification failed");
        }

        System.out.println("✅ Notification sent successfully");
    }

    private boolean sendNotification(String message) {

        // 👇 Failure simulate karne ke liye (important)
        if (message.contains("\"patientId\":9999")) {
            System.out.println("❌ Simulated notification failure");
            return false;
        }

        // Fake success
        System.out.println("📨 Sending Email/SMS...");
        return true;
    }

    }

