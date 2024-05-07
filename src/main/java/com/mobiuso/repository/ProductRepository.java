package com.mobiuso.repository;

import com.mobiuso.entity.Product;
import com.mobiuso.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByUser(User user);

    @Transactional
    void deleteAllByUser(User user);

    Product findByUserAndProductId(User user,String productId);
    @Transactional
    void deleteByProductId(String productId);
}
