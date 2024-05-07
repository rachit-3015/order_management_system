package com.mobiuso.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductClassDto {
    private String productClassDtoId;
    private ProductClassDescDto productClassDescDto;
}
