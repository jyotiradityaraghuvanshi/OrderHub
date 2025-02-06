package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItem;
import com.service.ordering.order.dto.ResponseDto.CartResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartListServiceClient {

    private final RestTemplate restTemplate;

    @Value("${wishlist.service.url}")
    private String wishListServiceUrl; // Read URL from properties file

    //Constructor Injection
    public CartListServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<CartItem> fetchCartItems(Integer cartId){

        String url = wishListServiceUrl + "/wishlist/" + cartId;

        ResponseEntity<CartResponseDto> response = restTemplate.getForEntity(url , CartResponseDto.class);

        return response.getBody().getCartItemDtoList();
    }


}
