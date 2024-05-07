package com.mobiuso.service;

import com.mobiuso.entity.Address;
import com.mobiuso.entity.User;
import com.mobiuso.request.AddressDto;
import org.springframework.stereotype.Service;

@Service
public interface UserAboutService {
    Address addressRequestToEntity(AddressDto addressRequest);
    AddressDto addressEntityToRequest(Address address);
    void modifyTheAddress(User user, Address address);
}
