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

    public IdentityResponseDto checkUserValidation(Integer userId){

        if (test) {
            // In test mode, return a dummy IdentityResponseDto with test data.
            IdentityResponseDto dummyResponse = new IdentityResponseDto();
            // Populate the dummy response with example test data.
            dummyResponse.setUserId(userId);
            dummyResponse.setEmail("dummy@example.com");
            dummyResponse.setUserName("Dummy User");
            dummyResponse.setLocation("UK");
            // Set additional fields if necessary.
            return dummyResponse;
        }

        String url = identityClientUrl + "/identity/" + userId;

        ResponseEntity<IdentityResponseDto> response = restTemplate.getForEntity(url , IdentityResponseDto.class);

        return response.getBody();
    }

}
