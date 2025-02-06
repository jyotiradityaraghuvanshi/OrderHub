package com.service.ordering.order.repository;

import com.service.ordering.order.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistory, Integer> {

    List<OrderHistory> findByUserId(Integer userId);
    List<OrderHistory> findByOrderId(Integer orderId);
}
