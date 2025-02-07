package com.service.ordering.order.Utils;

import com.service.ordering.order.Enum.Status;
import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.InventoryItemDto;
import com.service.ordering.order.dto.PriceItemDto;
import com.service.ordering.order.dto.RequestDto.OrderRequestDto;
import com.service.ordering.order.dto.ResponseDto.OrderResponseDto;
import com.service.ordering.order.dto.ResponseDto.PricingResponseDto;
import com.service.ordering.order.entity.Order;
import com.service.ordering.order.exception.ProductOutOfStockException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class OrderServiceUtils {

    public static Order createOrder(OrderRequestDto orderRequestDto , Integer totalPrice){
        Order order = new Order();
        order.setUserId(orderRequestDto.getUserId());
        order.setTotalAmount(totalPrice);
        order.setOrderStatus(Status.CREATED);

        return order;
    }

    public static List<Integer> getProductIdsList(List<CartItemDto> cartItems){
        // extracting the product List from cart items list.
        List<Integer> productIdsList = cartItems.stream()
                .map(CartItemDto :: getProductId)
                .toList();
        return productIdsList;
    }

    public static Optional<String> compareInventoryItems(List<CartItemDto> cartList , List<InventoryItemDto> inventoryList){

        // here we are considering that both the list is of same size because the inventory team will only return
        // those products that are in cart.*/

        if(inventoryList.size() == cartList.size()) {

            for (int i = 0; i < cartList.size(); i++) {

                int cartQuantity = cartList.get(i).getQuantity();
                int inventoryQuantity = inventoryList.get(i).getQuantity();

                if (inventoryQuantity < cartQuantity) {
                    // here think how we can return that product whose quantity is less than the required quantity -> handled.
                    return Optional.of(cartList.get(i).productId.toString());
                }

            }
        }
        else{
            return Optional.of("some products are missing in inventory");
        }

        return Optional.empty();
    }

    public static int getTotalPrice(PricingResponseDto priceList, List<CartItemDto> cartItems) {
        HashMap<Integer , Integer> priceMap = new HashMap<>();

        for(PriceItemDto items : priceList.getPriceList()){
            priceMap.put(items.getProductId() , items.getPrice());
        }

        int totalPrice = 0; // calculating the Total Price to pay.
        for(CartItemDto items : cartItems){

            int productId = items.getProductId();
            int quantity = items.getQuantity();

            if(!priceMap.containsKey(productId)){
                throw new ProductOutOfStockException("Product with productId :" + productId + " is Not available");
            }

            totalPrice += priceMap.get(productId) * quantity;

        }
        return totalPrice;
    }

}
