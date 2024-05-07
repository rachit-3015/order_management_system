package com.mobiuso.repository;

import com.mobiuso.entity.ProductClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ProductClassRepository extends JpaRepository<ProductClass,String> {

}
