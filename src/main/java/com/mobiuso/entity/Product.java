package com.mobiuso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;
    private String productName;
    private String productPrice;
    private String productDesc;
    private String productQuantityAvail;
    private double length;
    private double width;
    private double height;
    private double weight;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "product_productclass_id")
    private ProductClass productClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_user_id")
    @JsonIgnore
    private User user;
}
