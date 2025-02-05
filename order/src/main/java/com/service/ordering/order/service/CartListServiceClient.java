package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.ResponseDto.WishListResponseDto;
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

    public List<CartItemDto> fetchCartItems(Integer cartId){

        String url = wishListServiceUrl + "/wishlist/" + cartId;

        ResponseEntity<WishListResponseDto> response = restTemplate.getForEntity(url , WishListResponseDto.class);

        return response.getBody().getCartItemDtoList();
    }


}
