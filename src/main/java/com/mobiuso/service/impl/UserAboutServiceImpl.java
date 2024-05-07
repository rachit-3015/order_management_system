package com.mobiuso.service.impl;

import com.mobiuso.entity.Address;
import com.mobiuso.entity.User;
import com.mobiuso.repository.AddressRepository;
import com.mobiuso.request.AddressDto;
import com.mobiuso.service.UserAboutService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAboutServiceImpl implements UserAboutService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public Address addressRequestToEntity(AddressDto addressRequest) {
        Address address = this.modelMapper.map(addressRequest,Address.class);
        return address;
    }

    @Override
    public AddressDto addressEntityToRequest(Address address) {
        AddressDto addressRequest = this.modelMapper.map(address, AddressDto.class);
        return addressRequest;
    }

    @Override
    public void modifyTheAddress(User user, Address address) {
        Address addressFromDb = this.addressRepository.findByUser(user);
        System.out.println(address.toString());
        System.out.println("_-------------------------------------------------_____---------------____");
        System.out.println(addressFromDb.toString());
        address.setAddressId(addressFromDb.getAddressId());
        address.setUser(user);
        System.out.println("________--------------------------------------------------------------");
        System.out.println(address.toString());
        this.addressRepository.save(address);
    }
}
