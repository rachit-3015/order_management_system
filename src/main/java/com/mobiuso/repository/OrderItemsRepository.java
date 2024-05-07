package com.mobiuso.repository;

import com.mobiuso.entity.OrderItems;
import com.mobiuso.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemsRepository extends JpaRepository<OrderItems,String> {
    Optional<List<OrderItems>> findAllByUser(User user);
    Optional<OrderItems> findByUser(User user);
}
