package com.service.ordering.orders.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

// Change url later on
@FeignClient(name = "pricing-service", url = "http://localhost:8084")
public interface PricingServiceClient {

    @GetMapping("/pricing/products/{productId}/price")
    BigDecimal getProductPrice(@PathVariable String productId);
}
