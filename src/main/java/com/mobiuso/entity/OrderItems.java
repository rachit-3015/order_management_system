package com.mobiuso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @OneToMany
    List<Product> productList;

    private int productQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderitems_user_id")
    @JsonIgnore
    private User user;
}
