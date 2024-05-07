package com.mobiuso.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class ProductClass {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productClassId;
    @Enumerated(EnumType.STRING)
    private ProductClassDesc productClassDesc;

}
