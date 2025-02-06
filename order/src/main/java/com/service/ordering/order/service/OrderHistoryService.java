package com.service.ordering.order.service;

import com.service.ordering.order.entity.OrderHistory;
import com.service.ordering.order.repository.OrderHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class OrderHistoryService {

    @Autowired
     private OrderHistoryRepo orderHistoryRepo;

    public OrderHistory saveOrderHistory(Integer userId, Integer orderId, String status){
        OrderHistory orderHistory =  new OrderHistory();
              orderHistory.setOrderId(orderId);
              orderHistory.setUserId(userId);
              orderHistory.setStatus(status);
              orderHistory.setCreatedAt(LocalDateTime.now());
        return orderHistoryRepo.save(orderHistory);
    }
    public List<OrderHistory> getOrderHistoryByUserId(Integer userId){
        return orderHistoryRepo.findByUserId(userId);
    }
    public List<OrderHistory> getOrderHistoryByOrderId(Integer orderId){
        return  orderHistoryRepo.findByOrderId(orderId);
    }

}
