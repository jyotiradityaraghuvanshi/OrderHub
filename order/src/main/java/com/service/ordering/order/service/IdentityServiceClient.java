package com.service.ordering.order.service;


import com.service.ordering.order.dto.ResponseDto.IdentityResponseDto;
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

    public String checkUserValidation(Integer userId){

        if (test) {
            // In test mode, return a dummy token with test data.

            return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEwMSwiZW1haWwiOiJ0ZXN0QGVtYWlsLmNvbSIsInVzZXJOYW1lIjoiVGVzdCBVc2VyIiwibG9jYXRpb24iOiJVUyJ9._your_secret_here_";
        }

        String url = identityClientUrl + "/user/get/" + userId;

        ResponseEntity<String> response = restTemplate.getForEntity(url , String.class);

        return response.getBody();
    }

}
