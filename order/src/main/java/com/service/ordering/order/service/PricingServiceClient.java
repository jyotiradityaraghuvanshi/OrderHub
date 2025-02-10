package com.service.ordering.order.service;


import com.service.ordering.order.dto.PriceItemDto;
import com.service.ordering.order.dto.ResponseDto.PricingResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PricingServiceClient {

    private final RestTemplate restTemplate;

    @Value("${pricing.service.url}")
    private String pricingServiceUrl;

    @Value("${test:false}")
    private boolean test;

    public PricingServiceClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public PricingResponseDto getPricingList(List<Integer> productList){
        if (test) {
            // Create a dummy PricingResponseDto with test data
            PricingResponseDto dummyResponse = new PricingResponseDto();
            List<PriceItemDto> dummyPriceList = new ArrayList<>();
            // For each product id, assign a dummy price (e.g., 50)
            for (Integer productId : productList) {
                PriceItemDto dummyPriceItem = new PriceItemDto();
                dummyPriceItem.setProductId(productId);
                dummyPriceItem.setPrice(50); // Dummy price for testing
                dummyPriceList.add(dummyPriceItem);
            }
            dummyResponse.setPriceList(dummyPriceList);
            return dummyResponse;
        }

        String url = pricingServiceUrl + "/pricing";

        ResponseEntity<PricingResponseDto> response = restTemplate.postForEntity(url , productList , PricingResponseDto.class);

        return response.getBody();
    }


}
