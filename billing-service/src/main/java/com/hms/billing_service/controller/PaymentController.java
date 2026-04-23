package com.hms.billing_service.controller;

import com.hms.billing_service.entity.Invoice;
import com.hms.billing_service.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ✅ Mark invoice as PAID
    @PostMapping("/{invoiceId}/pay")
    public Invoice pay(@PathVariable Long invoiceId) {
        return paymentService.markPaid(invoiceId);
    }

    // ❌ Mark invoice as FAILED
    @PostMapping("/{invoiceId}/fail")
    public Invoice fail(@PathVariable Long invoiceId) {
        return paymentService.markFailed(invoiceId);
    }
}
