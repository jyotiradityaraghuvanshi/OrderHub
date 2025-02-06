package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItem;
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

    public InventoryResponseDto getItemsAvailability(List<CartItem> cartItemDto){

        // here: think how are we going to give the list of Items received from Cart to Inventory, because we cannot
        // pass the list directly in the URL.*/
        String url = inventoryClientUrl + "/inventory/" ;

        ResponseEntity<InventoryResponseDto> response = restTemplate.getForEntity(url , InventoryResponseDto.class);

        return response.getBody();
    }


}
