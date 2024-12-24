package com.HBauction.webapp.service;

import com.HBauction.webapp.model.Payment;
import com.HBauction.webapp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * A service which saves a payment to the paymentRepository
 */
@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
