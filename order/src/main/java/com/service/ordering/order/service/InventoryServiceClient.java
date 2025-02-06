package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItemDto;
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


    @Value("${test:false}")
    private boolean test;


    public InventoryServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public InventoryResponseDto getItemsAvailability(List<CartItemDto> cartItemDto){

        if (test) {
            InventoryResponseDto dummyResponse = new InventoryResponseDto();

            return dummyResponse;
        }

        String url = inventoryClientUrl + "/inventory/check-stock" ;

        ResponseEntity<InventoryResponseDto> response = restTemplate.postForEntity(url, cartItemDto, InventoryResponseDto.class);

        // test will be written by adarsh shekhar
        return response.getBody();
    }




}
