package com.service.ordering.orders.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Change url later on
@FeignClient(name = "identity-service", url = "http://localhost:8081")
public interface IdentityServiceClient {

    @GetMapping("/identity/users/{userId}/verify")
    boolean verifyUser(@PathVariable String userId);

    @GetMapping("/identity/users/{userId}/userName")
    String getUserName(@PathVariable String userId);
}
