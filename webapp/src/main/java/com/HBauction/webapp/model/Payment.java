package com.HBauction.webapp.model;

import jakarta.persistence.*;
import lombok.Data;

/*
 * The Payment model class
 * Payment has id, user_id, item_id, amountPaid, cardNumber, cardHolderName,
 * expiryDate and securityCode.
 */
@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private double amountPaid;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    private String securityCode;
}
