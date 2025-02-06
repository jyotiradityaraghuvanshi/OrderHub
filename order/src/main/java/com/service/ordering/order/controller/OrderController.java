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

        //Exception are handled globally no need to handle it and no need to use any try catch block.
        return new ResponseEntity<>(systemResponse , HttpStatus.CREATED);

    }


    @GetMapping("/view")
    public ResponseEntity<OrderResponseDto> viewOrder(@RequestParam Integer orderId){
        return new ResponseEntity<>(orderService.viewOrderDetails(orderId) , HttpStatus.OK);
    }


    public ResponseEntity<OrderResponseDto> cancelOrder(@RequestParam Integer orderId){
        return new ResponseEntity<>(orderService.cancelOrder(orderId) , HttpStatus.ACCEPTED);
    }


}
