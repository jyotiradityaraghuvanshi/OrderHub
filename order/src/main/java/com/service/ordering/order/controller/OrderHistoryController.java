package com.service.ordering.order.controller;

import com.service.ordering.order.entity.OrderHistory;
import com.service.ordering.order.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-history")
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @PostMapping("/save")
    public ResponseEntity<OrderHistory> saveOrderHistory(@RequestParam Integer userId,
                                                         @RequestParam Integer orderId,
                                                         @RequestParam String status){
        return ResponseEntity.ok(orderHistoryService.saveOrderHistory(userId,orderId,status));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderHistory>> getOrderHistoryByUserId(@PathVariable Integer userId){
        return ResponseEntity.ok(orderHistoryService.getOrderHistoryByUserId(userId));
    }

    @GetMapping("/user/{orderId}")
    public ResponseEntity<List<OrderHistory>> getOrderHistoryByOrderId(@PathVariable Integer orderId){
        return ResponseEntity.ok(orderHistoryService.getOrderHistoryByOrderId(orderId));
    }
}
