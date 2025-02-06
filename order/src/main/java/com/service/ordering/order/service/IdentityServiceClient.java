package com.service.ordering.order.service;


import com.service.ordering.order.dto.ResponseDto.IdentityResponseDto;
import com.service.ordering.order.dto.ResponseDto.InventoryResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IdentityServiceClient {

    private final RestTemplate restTemplate;

    @Value("${identity.service.url}")
    private String identityClientUrl;

    @Value("${test:false}")
    private boolean test;

    public IdentityServiceClient(RestTemplateBuilder templateBuilder){
        this.restTemplate = templateBuilder.build();
    }

    public IdentityResponseDto checkUserValidation(Integer userId){
        if (test) {
            IdentityResponseDto dummyResponse = new IdentityResponseDto();

            return dummyResponse;
        }

        String url = identityClientUrl + "/identity/" + userId;

        ResponseEntity<IdentityResponseDto> response = restTemplate.getForEntity(url, IdentityResponseDto.class);
        return response.getBody();
    }

}
