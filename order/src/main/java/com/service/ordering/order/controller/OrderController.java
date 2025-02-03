package com.service.ordering.order.controller;


import com.service.ordering.order.dto.RequestDto.OrderRequestDto;
import com.service.ordering.order.service.OrderService;
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
        Boolean success = orderService.createOrdering(orderRequestDto);
        if(success) return new ResponseEntity<>(true, HttpStatus.CREATED);
        else return new ResponseEntity<>(false, HttpStatus.EXPECTATION_FAILED);

    }




}
