package com.HBauction.webapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/*
 * Item model class
 * item has an id, name, description, startingPrice, auctionType, currentPrice,
 * shippingTime, shippingPrice, expeditedShippingCost, auctionEndTime, highestBidder
 * and remainingTime.
 */
@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    private String description;

    @Column(nullable = false)
    private double startingPrice;

    @Column(nullable = false)
    private String auctionType; // "Forward" or "Dutch"

    @Column(nullable = false)
    private double currentPrice;

    @Column(nullable = false)
    private int shippingTime; 

    @Column(nullable = false)
    private double shippingPrice; 

    @Column(nullable = false)
    private double expeditedShippingCost; 

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date auctionEndTime;

    @ManyToOne
    @JoinColumn(name = "highest_bidder_id") 
    private User highestBidder;

    @Transient
    private String remainingTime; 
}

