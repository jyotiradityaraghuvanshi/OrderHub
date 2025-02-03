package com.service.ordering.order.service;


import com.service.ordering.order.dto.RequestDto.OrderRequestDto;
import com.service.ordering.order.repository.OrderRepo;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Builder
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;


    public Boolean createOrdering(OrderRequestDto orderRequestDto) {

        //Step 1-> First check user by the api with Identity team , whether the user is valid or not.
        /* get/UserValidation(orderRequestDto.getUserId()) , if the response is true then proceed further
        * else return Exception -INVALIDUserException */


        /* Step 2-> Fetch List of Products and their quantity from WishList Service ,  */

        /* Step 3-> Check the Product availability from Inventory Service by providing ProductId and product
        * Quantity.  */

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
