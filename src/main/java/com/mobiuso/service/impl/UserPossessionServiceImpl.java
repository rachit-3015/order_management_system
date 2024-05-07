package com.mobiuso.service.impl;

import com.mobiuso.entity.Product;
import com.mobiuso.request.ProductDto;
import com.mobiuso.service.UserPossessionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class UserPossessionServiceImpl implements UserPossessionService {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product productRequestToEntity(ProductDto productRequest) {
        Product product = this.modelMapper.map(productRequest,Product.class);
        return product;
    }

    @Override
    public List<ProductDto> productListEntityToRequest(List<Product> productList) {
        Type targetListType = new TypeToken<List<ProductDto>>() {}.getType();
        List<ProductDto> productDtoList = modelMapper.map(productList, targetListType);
        return productDtoList;
    }
}
