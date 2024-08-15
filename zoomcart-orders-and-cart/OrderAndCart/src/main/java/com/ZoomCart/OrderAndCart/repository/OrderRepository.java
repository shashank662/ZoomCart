package com.ZoomCart.OrderAndCart.repository;

import com.ZoomCart.OrderAndCart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, Integer> {
    List<Order> findByUserId(int userId);

}
