package com.mobiuso.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private String productId;
    private String productName;
    private String productPrice;
    private String productDesc;
    private String productQuantityAvail;
    private double length;
    private double width;
    private double height;
    private double weight;
    private ProductClassDto productClassDto;
}
