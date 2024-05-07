package com.mobiuso.service;

import com.mobiuso.entity.Product;
import com.mobiuso.request.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserPossessionService {
    Product productRequestToEntity(ProductDto productRequest);
    List<ProductDto> productListEntityToRequest(List<Product> productList);
}
