package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.InventoryMerchantDto;
import com.service.ordering.order.dto.ResponseDto.InventoryResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InventoryServiceClient {

    private final RestTemplate restTemplate;


    @Value("${inventory.service.url}")
    private String inventoryClientUrl;


    public InventoryServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public InventoryResponseDto getItemsAvailability(List<Integer> productIdsList , String pinCode){

        // here: think how are we going to give the list of Items received from Cart to Inventory, because we cannot
        // pass the list directly in the URL.*/
        String url = inventoryClientUrl + "/inventory/check-stock" + pinCode;

        // handled the issue of sending the list of cartItems to Inventory
        ResponseEntity<InventoryResponseDto> response = restTemplate.postForEntity(url , productIdsList , InventoryResponseDto.class);

        // test will be written by adarsh shekhar
        return response.getBody();
    }


    public InventoryResponseDto performInventoryOperation(List<CartItemDto> cartItems){

        String url = inventoryClientUrl + "/inventory/operation";

        ResponseEntity<InventoryResponseDto> response = restTemplate.postForEntity(url , cartItems , InventoryResponseDto.class);

        return response.getBody();
    }



}
