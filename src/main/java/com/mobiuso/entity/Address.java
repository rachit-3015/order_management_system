package com.mobiuso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String addressId;
    private String address;
    private String city;
    private String state;
    private int pincode;
    private String country;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
