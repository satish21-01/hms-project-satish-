package com.hms.billing_service.service;

import com.hms.billing_service.dto.PaymentStatusEvent;
import com.hms.billing_service.entity.Invoice;
import com.hms.billing_service.entity.InvoiceStatus;
import com.hms.billing_service.kafka.PaymentEventProducer;
import com.hms.billing_service.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentEventProducer paymentEventProducer;

    public PaymentService(
            InvoiceRepository invoiceRepository,
            PaymentEventProducer paymentEventProducer
    ) {
        this.invoiceRepository = invoiceRepository;
        this.paymentEventProducer = paymentEventProducer;
    }

    public Invoice markPaid(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoice.setStatus(InvoiceStatus.PAID);
        Invoice saved = invoiceRepository.save(invoice);

        PaymentStatusEvent event = PaymentStatusEvent.builder()
                .invoiceId(saved.getId())
                .appointmentId(saved.getAppointmentId())
                .patientId(saved.getPatientId())
                .amount(saved.getAmount())
                .status("PAID")
                .updatedAt(LocalDateTime.now())
                .build();

        paymentEventProducer.publish(event);

        return saved;
    }

    public Invoice markFailed(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoice.setStatus(InvoiceStatus.FAILED);
        Invoice saved = invoiceRepository.save(invoice);

        PaymentStatusEvent event = PaymentStatusEvent.builder()
                .invoiceId(saved.getId())
                .appointmentId(saved.getAppointmentId())
                .patientId(saved.getPatientId())
                .amount(saved.getAmount())
                .status("FAILED")
                .updatedAt(LocalDateTime.now())
                .build();

        paymentEventProducer.publish(event);

        return saved;
    }
}



