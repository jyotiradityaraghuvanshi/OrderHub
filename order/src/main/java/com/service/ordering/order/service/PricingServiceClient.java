package com.service.ordering.order.service;


import com.service.ordering.order.dto.ResponseDto.PricingResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PricingServiceClient {

    private final RestTemplate restTemplate;

    @Value("${pricing.service.url}")
    private String pricingServiceUrl;

    public PricingServiceClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public PricingResponseDto getPricingList(List<Integer> productList){

        String url = pricingServiceUrl + "/pricing";

        ResponseEntity<PricingResponseDto> response = restTemplate.postForEntity(url , productList , PricingResponseDto.class);

        return response.getBody();
    }


}
