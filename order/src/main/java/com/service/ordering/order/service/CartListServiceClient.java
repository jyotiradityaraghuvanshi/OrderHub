package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.ResponseDto.CartListResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartListServiceClient {

    private final RestTemplate restTemplate;

    @Value("${cartlist.service.url}")
    private String wishListServiceUrl; // Read URL from properties file

    @Value("${test:false}")
    private boolean test;

    //Constructor Injection
    public CartListServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<CartItemDto> fetchCartItems(Integer cartId){

        if (test) {
            CartListResponseDto dummyResponse = new CartListResponseDto();
            List<CartItemDto> dummyItems = new ArrayList<>();

            // Create a dummy CartItemDto and set its test values.
            CartItemDto dummyItem = new CartItemDto();
            dummyItem.setProductId(101);
            dummyItem.setQuantity(1);
            // Populate additional fields if required.

            dummyItems.add(dummyItem);
            dummyResponse.setCartItemDtoList(dummyItems);

            return dummyResponse.getCartItemDtoList();
        }



        String url = wishListServiceUrl + "/cartlist/" + cartId; //

        ResponseEntity<CartListResponseDto> response = restTemplate.getForEntity(url , CartListResponseDto.class);

        return response.getBody().getCartItemDtoList();
    }


}
