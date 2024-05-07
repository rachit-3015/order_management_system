package com.mobiuso.repository;

import com.mobiuso.entity.Address;
import com.mobiuso.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Address findByUser(User user);

}

