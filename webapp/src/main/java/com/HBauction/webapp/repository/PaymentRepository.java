package com.HBauction.webapp.repository;

import com.HBauction.webapp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * JpaRepository for Payment
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
