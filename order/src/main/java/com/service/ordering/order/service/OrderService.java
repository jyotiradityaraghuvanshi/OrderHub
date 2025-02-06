package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItem;
import com.service.ordering.order.dto.InventoryItemDto;
import com.service.ordering.order.dto.RequestDto.OrderRequestDto;
import com.service.ordering.order.dto.ResponseDto.IdentityResponseDto;
import com.service.ordering.order.dto.ResponseDto.InventoryResponseDto;
import com.service.ordering.order.dto.ResponseDto.OrderResponseDto;
import com.service.ordering.order.repository.OrderRepo;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Builder
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartListServiceClient cartListServiceClient;


    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    private IdentityServiceClient identityServiceClient;

    public OrderResponseDto createOrdering(@NonNull OrderRequestDto orderRequestDto) {

        //Step 1-> First check user by the api with Identity team , whether the user is valid or not.
        /* get/UserValidation(orderRequestDto.getUserId()) , if the response is true then proceed further
        * else return Exception -INVALIDUserException */

        IdentityResponseDto user = identityServiceClient.checkUserValidation(orderRequestDto.getUserId());
        if(user.getEmail() == null){
            // throw new Exception INVALID_USER_EXCEPTION("message");
            return null;
        }



        /* Step 2-> Fetch List of Products and their quantity from WishList Service , Call the wishList by giving
        * the cartId and catch the List<Items> itemsList which contains productId , Quantity */

        // we'll use the try catch block here and in every other calls too.
        List<CartItem> cartItems = cartListServiceClient.fetchCartItems(orderRequestDto.getCartId());

        if(cartItems.isEmpty()){
            // throw new Exception Empty_Cart_Exception("message");

            return null;
        }



        /* Step 3-> Check the Product availability from Inventory Service by providing ProductId and product
        * Quantity.  */

        // we are not passing the single-single products, this time we are passing the whole list of products to Inventory.
        InventoryResponseDto inventoryItems = inventoryServiceClient.getItemsAvailability(cartItems);

        if(inventoryItems.getInventoryItemList().isEmpty()){
            // throw new Exception InventoryServiceException("message").

            return null;
        }
        // we are checking whether the inventory items list has enough stock for fulfilling the order or not
        Boolean check = compareInventoryItems(cartItems , inventoryItems.getInventoryItemList());

        if(!check){
            // throw new Exception ProductOutOfStockException("message").
            return null;
        }

//        for(CartItemDto items : cartItems){
//
//            if(!inventoryServiceClient.getItemsAvailability(items)){
//                // throw Exception !ProductNotAvailableException("message").
//                return null;
//            }
//
//        }





        /*Step 4 -> Get all the products prices from Pricing Service from their corresponding productId */

        /*Step 5 ->We have to create this no external service required , Work is -: Generate Invoice by calculating
        * the price for each products accordance to their quantity and the Total Amount to be paid by adding all
        * the products prices  */

        /*Step 6 -> Provide the Invoice and Total Amount to Payment Service and awaits for response SUCCESS/FAILURE  */

        /* Step 7-> Order successfully created now create(save) the order in the database */

        // SAVE product details in OrderItem table (KUNDAN)

        /*Step 8-> Notify services like: Inventory , User , Delivery, WishList ,etc.
         about the Order being successfully created. */

        return null;
    }


    public Boolean compareInventoryItems(List<CartItem> cartList , List<InventoryItemDto> inventoryList){

        // here we are considering that both the list is of same size because the inventory team will only return
        // those products that are in cart.*/
        for(int i=0;i<cartList.size();i++){

            int cartQuantity = cartList.get(i).getQuantity();
            int invetoryQuantity = inventoryList.get(i).getQuantity();

            if(invetoryQuantity < cartQuantity) {
                // here think how we can return that product whose quantity is less than the required quantity
                return false;
            }

        }

        return true;
    }





}
