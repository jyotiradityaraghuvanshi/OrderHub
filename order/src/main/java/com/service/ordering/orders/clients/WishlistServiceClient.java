package com.service.ordering.orders.clients;

import com.service.ordering.orders.dto.RequestDto.CartRequest;
import com.service.ordering.orders.dto.ResponseDto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Change url later on
@FeignClient(name = "wishlist-service", url = "http://localhost:8081")
public interface WishlistServiceClient {

    @GetMapping("/wishlist/{cartId}")
    CartResponse getCartDetails(@RequestBody CartRequest cartRequest);
}
