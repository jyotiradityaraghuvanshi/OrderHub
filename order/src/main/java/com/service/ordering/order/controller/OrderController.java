package com.service.ordering.order.controller;


import com.service.ordering.order.dto.RequestDto.OrderRequestDto;
import com.service.ordering.order.dto.ResponseDto.OrderResponseDto;
import com.service.ordering.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        OrderResponseDto systemResponse = orderService.createOrdering(orderRequestDto);

        if(systemResponse != null){
            return new ResponseEntity<>(systemResponse , HttpStatus.CREATED);
        }
        else {
            OrderResponseDto emptyResponse = new OrderResponseDto();
            return new ResponseEntity<>(emptyResponse , HttpStatus.BAD_REQUEST);
        }

    }




}
