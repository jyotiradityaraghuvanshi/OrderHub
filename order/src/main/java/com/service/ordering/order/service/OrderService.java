package com.service.ordering.order.service;


import com.service.ordering.order.Enum.Status;
import com.service.ordering.order.Utils.OrderServiceUtils;
import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.InventoryItemDto;
import com.service.ordering.order.dto.InventoryMerchantDto;
import com.service.ordering.order.dto.PriceItemDto;
import com.service.ordering.order.dto.RequestDto.InventoryUpdateRequestDto;
import com.service.ordering.order.dto.RequestDto.OrderRequestDto;
import com.service.ordering.order.dto.ResponseDto.IdentityResponseDto;
import com.service.ordering.order.dto.ResponseDto.InventoryResponseDto;
import com.service.ordering.order.dto.ResponseDto.OrderResponseDto;
import com.service.ordering.order.dto.ResponseDto.PricingResponseDto;
import com.service.ordering.order.entity.Order;
import com.service.ordering.order.exception.*;
import com.service.ordering.order.repository.OrderRepo;
import lombok.Builder;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Builder
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private CartListServiceClient cartListServiceClient;

    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    private IdentityServiceClient identityServiceClient;

    @Autowired
    private PricingServiceClient pricingServiceClient;

    @Autowired
    private PaymentServiceClient paymentServiceClient;


    public OrderResponseDto createOrdering(@NonNull OrderRequestDto orderRequestDto){

        //Step 1-> First check user by the api with Identity team , whether the user is valid or not.
        /* get/UserValidation(orderRequestDto.getUserId()) , if the response is true then proceed further
         * else return Exception -INVALIDUserException */

        IdentityResponseDto user = identityServiceClient.checkUserValidation(orderRequestDto.getUserId());
        if(user.getEmail() == null){
            throw new InvalidUserException("User Id" + orderRequestDto.getUserId() + "Is Invalid");
        }



        /* Step 2-> Fetch List of Products and their quantity from WishList Service , Call the wishList by giving
         * the cartId and catch the List<Items> itemsList which contains productId , Quantity */

        // we'll use the try catch block here and in every other calls too.
        List<CartItemDto> cartItems = cartListServiceClient.fetchCartItems(orderRequestDto.getCartId());

        if(cartItems.isEmpty()){
            throw new CartEmptyException("Cart is empty for Cart ID: " + orderRequestDto.getCartId());

        }


        // extracting the product List from cart items list.
        List<Integer> productIdsList = OrderServiceUtils.getProductIdsList(cartItems);

        /* Step 3-> Check the Product availability from Inventory Service by providing ProductId and product
         * Quantity.  */

        // we are not passing the single-single products, this time we are passing the whole list of products to Inventory.
        InventoryResponseDto inventoryItems = inventoryServiceClient.getItemsAvailability(productIdsList , orderRequestDto.getPinCode());

        if(inventoryItems.getInventoryItemList().isEmpty()){
            throw new InventoryServiceException("Inventory Service Return Empty Stock");
        }

        // we are checking whether the inventory items list has enough stock for fulfilling the order or not
        Optional<String> productStock = OrderServiceUtils.compareInventoryItems(cartItems , inventoryItems.getInventoryItemList());

        if(productStock.isPresent()){
            throw new ProductOutOfStockException("Product " + productStock.get() + " is out of stock from Inventory.");
        }




        /*Step 4 -> Get all the products prices from Pricing Service for their corresponding productId */


        // getting the priceList from Pricing team.
        PricingResponseDto priceList = pricingServiceClient.getPricingList(productIdsList);
        if (priceList.getPriceList().isEmpty()){
            throw new PriceNotAvailableException("Currently Prices are not available.");
        }

        // Now I will map the productId with their corresponding prices to know which price is of which product.
        int totalPrice = OrderServiceUtils.getTotalPrice(priceList, cartItems);



        /*Step 5 ->We have to create this no external service required , Work is -: Generate Invoice by calculating
         * the price for each products accordance to their quantity and the Total Amount to be paid by adding all
         * the products prices  */ // do not do the step 5 here , create a new service.
        // THIS STEP 5 WILL NOT BE PROCESSED HERE WE WILL CALL A INVOICE_SERVICE TO GENERATE INVOICE AFTER ORDER COMPLETION.



        /*Step 6 -> Provide the Invoice and Total Amount to Payment Service and awaits for response SUCCESS/FAILURE  */

        Boolean success = paymentServiceClient.processPayment(totalPrice);

        if(!success){
            throw new PaymentFailureException("PAYMENT FAILED!!");
        }

        // Call Inventory team to reduce some stocks.
        InventoryResponseDto inventoryResponseDto = inventoryServiceClient.performInventoryOperation(cartItems);

        List<InventoryMerchantDto> productMerchantList = inventoryResponseDto.getMerchantList();
        if(productMerchantList.isEmpty()){
            throw new InventoryServiceException("Merchants cannot be assigned for your Order");
        }

        // extract merchant List
        List<Integer> merchantIdsList = OrderServiceUtils.getMerchantIdsList(productMerchantList);

        /* Step 7-> successfully create the order now and save the order in the database */

        Order order = OrderServiceUtils.createOrder(orderRequestDto , totalPrice , merchantIdsList , user.getEmail());

        orderItemService.saveOrderItem(cartItems , order , priceList , productMerchantList);

        orderRepo.save(order);

        OrderResponseDto orderResponseDto = convertEntityToDto(order);

        /*Step 8-> Notify services like: Inventory , User , Delivery, WishList ,etc.
         about the Order being successfully created. */

        return orderResponseDto;
    }



    public OrderResponseDto getOrderDetails(Integer orderId) {

        Optional<Order> order = orderRepo.findByOrderId(orderId);

        if(order.isEmpty()){
            throw new OrderNotFoundException("Order with the orderId :" + orderId + " does not exist");
        }

        return convertEntityToDto(order.get());

    }



    public OrderResponseDto cancelOrder(Integer orderId) {

        Optional<Order> orderOptional = orderRepo.findByOrderId(orderId);

        if(orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order with the orderId :" + orderId + " does not exist");
        }

        Order order = orderOptional.get();
        order.setOrderStatus(Status.CANCELLED);
        orderRepo.save(order);

        // calling Inventory Team for order being cancelled.
        List<InventoryUpdateRequestDto> restoreItems = order.getOrderItemsList()
                                                       .stream()
                                                       .map(item ->
                                                               new InventoryUpdateRequestDto(item.getProductId() , item.getQuantity(), item.getMerchantId()))
                                                       .toList();
        Boolean restoreOperation = inventoryServiceClient.restoreInventoryStock(restoreItems);
        if(!restoreOperation){
            throw new InventoryServiceException("Inventory stocks cannot be restored");
        }

        return convertEntityToDto(order);

    }


    private OrderResponseDto convertEntityToDto(Order order){
        return modelMapper.map(order , OrderResponseDto.class);
    }

    private Order convertDtoToEntity(OrderRequestDto orderRequestDto){
        return modelMapper.map(orderRequestDto , Order.class);
    }

}

