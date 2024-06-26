package com.mobiuso.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String shipperId;
    private String shipperName;
    private int shipperPhone;
    private String shipperAddress;
}
