package com.service.ordering.order.repository;

import com.service.ordering.order.entity.OrderItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem , Integer> {
}
