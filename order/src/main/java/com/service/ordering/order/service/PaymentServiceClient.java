package com.service.ordering.order.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceClient {

    private final RestTemplate restTemplate;

    @Value("${payment.service.url}")
    private String paymentUrl;

    @Value("${test:false}")
    private boolean test;

    public PaymentServiceClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public Boolean processPayment(Integer totalPrice){

        if (test) {
            // In test mode, simply return success without calling any external API.
            return true;
        }
        // just returning the payment as success.
        // write test here adarsh shekhar.
        return true;
    }


}
