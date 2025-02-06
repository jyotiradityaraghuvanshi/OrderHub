package com.service.ordering.order.repository;

import com.service.ordering.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order , Integer> {
    Optional<Order> findByOrderId(Integer orderId);
}
