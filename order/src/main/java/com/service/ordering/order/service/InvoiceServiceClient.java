package com.service.ordering.order.service;

import com.service.ordering.order.dto.RequestDto.PricingRequest;
import com.service.ordering.order.dto.ResponseDto.CartResponseDto;
import com.service.ordering.order.dto.ResponseDto.IdentityResponseDto;
import com.service.ordering.order.dto.ResponseDto.PricingResponse;
import com.service.ordering.order.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class InvoiceServiceClient {

    private final RestTemplate restTemplate;

    // Fetching URLs from application.properties
    @Value("${identity.service.url}")
    private String identityServiceUrl;

    @Value("${wishlist.service.url}")
    private String cartServiceUrl;

    @Value("${pricing.service.url}")
    private String pricingServiceUrl;

    public InvoiceServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Calls IdentityService to fetch user details
    public IdentityResponseDto getUserDetails(Integer userId) throws ServiceUnavailableException {
        // confirm api contract and modify
        String url = identityServiceUrl + "/identity/user-details/" + userId;
        try {
            ResponseEntity<IdentityResponseDto> response = restTemplate.getForEntity(url, IdentityResponseDto.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceUnavailableException("Error calling IdentityService", e);
        }
    }

    // Calls CartService to fetch user's cart details
    public CartResponseDto getCartDetails(Integer userId) throws ServiceUnavailableException {
        // confirm api contract and modify
        String url = cartServiceUrl + "/cart/cart-details/" + userId;
        try {
            ResponseEntity<CartResponseDto> response = restTemplate.getForEntity(url, CartResponseDto.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceUnavailableException("Error calling CartService", e);
        }
    }

    // Calls PricingService to fetch pricing details
    public PricingResponse getProductPrices(PricingRequest request) throws ServiceUnavailableException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PricingRequest> requestEntity = new HttpEntity<>(request, headers);

            ResponseEntity<PricingResponse> response = restTemplate.exchange(
                    pricingServiceUrl, HttpMethod.POST, requestEntity, PricingResponse.class
            );

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceUnavailableException("Error calling PricingService", e);
        }
    }
}
