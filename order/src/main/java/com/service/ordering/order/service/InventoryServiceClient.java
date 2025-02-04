package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.ResponseDto.InventoryResponseDto;
import com.service.ordering.order.dto.ResponseDto.WishListResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryServiceClient {

    private final RestTemplate restTemplate;


    @Value("${wishlist.service.url}")
    private String inventoryClientUrl;


    public InventoryServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Boolean checkAvailability(CartItemDto cartItemDto){

        String url = inventoryClientUrl + "/inventory/" + cartItemDto.getProductId() + "/" +  cartItemDto.getQuantity();

        ResponseEntity<InventoryResponseDto> response = restTemplate.getForEntity(url , InventoryResponseDto.class);

        return response.getBody().getIsAvail();
    }


}
