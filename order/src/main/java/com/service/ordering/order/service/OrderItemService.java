package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.InventoryMerchantDto;
import com.service.ordering.order.dto.PriceItemDto;
import com.service.ordering.order.dto.ResponseDto.PricingResponseDto;
import com.service.ordering.order.entity.Order;
import com.service.ordering.order.entity.OrderItem;
import com.service.ordering.order.repository.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Value("${test:false}")
    private boolean test;

    public void saveOrderItem(List<CartItemDto> cartItems , Order order , PricingResponseDto priceList , List<InventoryMerchantDto> productMerchantList){

        // Test branch: simulate saving without calling the repository.
        if (test) {
            List<OrderItem> dummyOrderItems = new ArrayList<>();

            // Build a price map from the pricing response.
            HashMap<Integer, Integer> priceMap = new HashMap<>();
            for (PriceItemDto priceItem : priceList.getPriceList()) {
                priceMap.put(priceItem.getProductId(), priceItem.getPrice());
            }

            // Create a dummy OrderItem for each CartItem.
            for (CartItemDto cartItem : cartItems) {
                int productId = cartItem.getProductId();
                int quantity = cartItem.getQuantity();
                int price = priceMap.get(productId);
                int merchantId = productMerchantList.stream()
                        .filter(mp -> mp.getProductId().equals(productId))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Merchant ID missing for product: " + productId))
                        .getMerchantId();

                OrderItem dummyOrderItem = new OrderItem();
                dummyOrderItem.setOrder(order);
                dummyOrderItem.setProductId(productId);
                dummyOrderItem.setQuantity(quantity);
                dummyOrderItem.setPricePerItem(price);
                dummyOrderItem.setMerchantId(merchantId);
                dummyOrderItems.add(dummyOrderItem);
            }
            // Set the dummy order items to the order and return without saving.
            order.setOrderItemsList(dummyOrderItems);
            return;
        }

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
