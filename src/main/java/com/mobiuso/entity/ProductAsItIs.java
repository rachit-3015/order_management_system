package com.mobiuso.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class ProductAsItIs {
    @Id
    private Long productId;

    private String productDesc;
    private Integer productClassCode;
    private Double productPrice;
    private Integer productQuantityAvail;
    private Integer len;
    private Integer width;
    private Integer height;
    private Double weight;

}
