package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.InventoryMerchantDto;
import com.service.ordering.order.dto.PriceItemDto;
import com.service.ordering.order.dto.ResponseDto.PricingResponseDto;
import com.service.ordering.order.entity.Order;
import com.service.ordering.order.entity.OrderItem;
import com.service.ordering.order.repository.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepo orderItemRepo;

    public void saveOrderItem(List<CartItemDto> cartItems , Order order , PricingResponseDto priceList , List<InventoryMerchantDto> productMerchantList){

        List<OrderItem> orderItems = new ArrayList<>();

        HashMap<Integer , Integer> priceMap = new HashMap<>();

        for(PriceItemDto items : priceList.getPriceList()){
            priceMap.put(items.getProductId() , items.getPrice());
        }

        OrderItem orderItem = new OrderItem();
        for (CartItemDto cartItem : cartItems) {
            int productId = cartItem.getProductId();
            int quantity = cartItem.getQuantity();
            int price = priceMap.get(productId);
            int merchantId = productMerchantList
                    .stream()
                    .filter(mp -> mp.getProductId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Merchant ID missing"))
                    .getMerchantId();


            orderItem.setOrder(order);
            orderItem.setProductId(productId);
            orderItem.setQuantity(quantity);
            orderItem.setPricePerItem(price);
            orderItem.setMerchantId(merchantId);
            orderItems.add(orderItem);
        }

        order.setOrderItemsList(orderItems);
        orderItemRepo.save(orderItem);




    }

}
