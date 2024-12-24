package com.HBauction.webapp.model;

import jakarta.persistence.*; 
import lombok.Data;

/*
 * The User model class
 * User has an id, username, password, firstName, lastName, streetAddress, streetNumber,
 * postalCode, city, province, country.
 */ 
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String streetAddress;
    
    @Column(nullable = false)
    private String streetNumber;
    
    @Column(nullable = false)
    private String postalCode;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String province; 
    
    @Column(nullable = false)
    private String country;
}
