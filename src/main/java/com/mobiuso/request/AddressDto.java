package com.mobiuso.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDto {
    private String address;
    private String city;
    private String state;
    private int pincode;
    private String country;
}
