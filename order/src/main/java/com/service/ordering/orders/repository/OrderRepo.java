package com.service.ordering.orders.repository;

import com.service.ordering.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order , Integer> {
}
