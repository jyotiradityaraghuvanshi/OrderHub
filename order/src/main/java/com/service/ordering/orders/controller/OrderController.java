package com.service.ordering.orders.controller;


import com.service.ordering.orders.dto.RequestDto.OrderRequestDto;
import com.service.ordering.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        return new ResponseEntity<>(orderService.createOrdering(orderRequestDto) , HttpStatus.CREATED);
    }




}
