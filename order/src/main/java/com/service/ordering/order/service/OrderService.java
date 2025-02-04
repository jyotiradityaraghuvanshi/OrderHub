package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.RequestDto.OrderRequestDto;
import com.service.ordering.order.repository.OrderRepo;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Builder
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private WishListServiceClient wishListServiceClient;


    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    private IdentityServiceClient identityServiceClient;

    public Boolean createOrdering(OrderRequestDto orderRequestDto) {

        //Step 1-> First check user by the api with Identity team , whether the user is valid or not.
        /* get/UserValidation(orderRequestDto.getUserId()) , if the response is true then proceed further
        * else return Exception -INVALIDUserException */

        Boolean isValid = identityServiceClient.checkUserValidation(orderRequestDto.getUserId());
        if(!isValid){
            // throw new Exception INVALIDUSEREXCEPTION("message");
            return false;
        }



        /* Step 2-> Fetch List of Products and their quantity from WishList Service , Call the wishList by giving
        * the cartId and catch the List<Items> itemsList which contains productId , Quantity */

        // we'll use the try catch block here and in every other calls too.
        List<CartItemDto> itemDtoList = wishListServiceClient.fetchCartItems(orderRequestDto.getCartId());



        /* Step 3-> Check the Product availability from Inventory Service by providing ProductId and product
        * Quantity.  */

        for(CartItemDto items : itemDtoList){

            if(!inventoryServiceClient.checkAvailability(items)){
                // throw Exception !ProductNotAvailableException("message").
                return false;
            }

        }





        /*Step 4 -> Get all the products prices from Pricing Service from their corresponding productId */

        /*Step 5 ->We have to create this no external service required , Work is -: Generate Invoice by calculating
        * the price for each products accordance to their quantity and the Total Amount to be paid by adding all
        * the products prices  */

        /*Step 6 -> Provide the Invoice and Total Amount to Payment Service and awaits for response SUCCESS/FAILURE  */

        /* Step 7-> Order successfully created now create(save) the order in the database */

        /*Step 8-> Notify services like: Inventory , User , Delivery, WishList ,etc.
         about the Order being successfully created. */

        return true;
    }





}
