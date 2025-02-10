package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.InventoryItemDto;
import com.service.ordering.order.dto.InventoryMerchantDto;
import com.service.ordering.order.dto.RequestDto.InventoryUpdateRequestDto;
import com.service.ordering.order.dto.ResponseDto.InventoryResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    public InventoryResponseDto getItemsAvailability(List<Integer> productIdsList , String pinCode){

        if (test) {
            // Create a dummy InventoryResponseDto using the new DTO structure
            InventoryResponseDto dummyResponse = new InventoryResponseDto();

            List<InventoryItemDto> dummyInventoryItems = new ArrayList<>();
            int q = 100;
            if (productIdsList != null) {
                for (Integer productId : productIdsList) {
                    InventoryItemDto dummyItem = new InventoryItemDto();
                    dummyItem.setProductId(productId);
                    dummyItem.setQuantity(q); // Dummy available quantity
                    q++;
                    dummyInventoryItems.add(dummyItem);
                }
            }
//            List<InventoryMerchantDto> dummyMerchantList = new ArrayList<>();
//            InventoryMerchantDto dummyMerchant = new InventoryMerchantDto();
//            dummyMerchant.setMerchantId(1);
//            dummyMerchantList.add(dummyMerchant);

            dummyResponse.setInventoryItemList(dummyInventoryItems);
//            dummyResponse.setMerchantList(dummyMerchantList);
            dummyResponse.setMerchantList(new ArrayList<>());
            return dummyResponse;
        }
        // here: think how are we going to give the list of Items received from Cart to Inventory, because we cannot
        // pass the list directly in the URL.*/
        String url = inventoryClientUrl + "/inventory/check-stock" + pinCode;

        // handled the issue of sending the list of cartItems to Inventory
        ResponseEntity<InventoryResponseDto> response = restTemplate.postForEntity(url , productIdsList , InventoryResponseDto.class);

        // test will be written by adarsh shekhar
        return response.getBody();
    }


    public InventoryResponseDto performInventoryOperation(List<CartItemDto> cartItems){

        if (test) {
            InventoryResponseDto dummyResponse = new InventoryResponseDto();
//            List<InventoryItemDto> dummyInventoryItems = new ArrayList<>();
//
//            if (cartItems != null) {
//                for (CartItemDto cartItem : cartItems) {
//                    InventoryItemDto dummyItem = new InventoryItemDto();
//                    // Assuming CartItemDto has getProductId() and getQuantity() methods.
//                    dummyItem.setProductId(cartItem.getProductId());
//                    dummyItem.setQuantity(cartItem.getQuantity());
//                    dummyInventoryItems.add(dummyItem);
//                }
//            }

            // Here we choose to leave the merchant list empty in test mode
            //dummyResponse.setInventoryItemList(dummyInventoryItems);

            List<InventoryMerchantDto> dummyMerchantList = new ArrayList<>();
            int mId = 201;
            for(CartItemDto items : cartItems){
                InventoryMerchantDto dummyMerchant = new InventoryMerchantDto();
                dummyMerchant.setProductId(items.getProductId());
                dummyMerchant.setMerchantId(mId);
                mId++;
                dummyMerchantList.add(dummyMerchant);
            }

            dummyResponse.setInventoryItemList(new ArrayList<>());
            dummyResponse.setMerchantList(dummyMerchantList);
            return dummyResponse;
        }

        String url = inventoryClientUrl + "/inventory/operation";

        ResponseEntity<InventoryResponseDto> response = restTemplate.postForEntity(url , cartItems , InventoryResponseDto.class);

        return response.getBody();
    }


    public Boolean restoreInventoryStock(List<InventoryUpdateRequestDto> restoreItemsList){

        if (test) {
            return true;
        }

        String url = inventoryClientUrl + "/inventory/restore";

        ResponseEntity<Boolean> response = restTemplate.postForEntity(url , restoreItemsList , Boolean.class);

        return response.getBody();
    }



}
