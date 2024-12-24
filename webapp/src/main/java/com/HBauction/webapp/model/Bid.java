package com.HBauction.webapp.model;

import jakarta.persistence.*;
import lombok.Data;

/*
 *Bid model
 *a bid will have a bid id, item_id, bidder_id and an amount
 */
@Entity
@Data
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bidder_id", nullable = false)
    private User bidder;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private double amount;
}
