package com.mobiuso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class OrderHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderHeaderId;
    private LocalDateTime orderDate = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;                    //I think for payment there should be a different table
    private LocalDateTime paymentDate;
    private LocalDateTime orderShipmentDate;

    @OneToOne
    @JoinColumn(name = "orderheader_shipper_id")
    private Shipper shipper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderheader_user_id")
    @JsonIgnore
    private User user;
}
