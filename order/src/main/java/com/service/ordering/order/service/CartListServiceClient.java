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
            // Create dummy CartItemDto list for testing purposes.
            List<CartItemDto> dummyCartItems = new ArrayList<>();

            // Create a dummy cart item
            CartItemDto dummyItem = new CartItemDto();
            // Set dummy values (adjust these based on your CartItemDto definition)
            dummyItem.setProductId(101);   // example dummy product id
            dummyItem.setQuantity(2);      // example dummy quantity

            dummyCartItems.add(dummyItem);

            // Optionally, add more dummy items if required.
            return dummyCartItems;
        }

        String url = wishListServiceUrl + "/cartlist/" + cartId; //

        ResponseEntity<CartListResponseDto> response = restTemplate.getForEntity(url , CartListResponseDto.class);

        return response.getBody().getCartItemDtoList();
    }


}
