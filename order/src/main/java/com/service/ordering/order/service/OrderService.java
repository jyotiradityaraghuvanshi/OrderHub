package com.service.ordering.order.service;


import com.service.ordering.order.Enum.Status;
import com.service.ordering.order.dto.CartItemDto;
import com.service.ordering.order.dto.InventoryItemDto;
import com.service.ordering.order.dto.PriceItemDto;
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



        /* Step 3-> Check the Product availability from Inventory Service by providing ProductId and product
         * Quantity.  */

        // we are not passing the single-single products, this time we are passing the whole list of products to Inventory.
        InventoryResponseDto inventoryItems = inventoryServiceClient.getItemsAvailability(cartItems);

        if(inventoryItems.getInventoryItemList().isEmpty()){
            throw new InventoryServiceException("Inventory Service Return Empty Stock");
        }

        // we are checking whether the inventory items list has enough stock for fulfilling the order or not
        Optional<String> productStock = compareInventoryItems(cartItems , inventoryItems.getInventoryItemList());

        if(productStock.isPresent()){
            throw new ProductOutOfStockException("Product " + productStock.get() + " is out of stock");
        }




        /*Step 4 -> Get all the products prices from Pricing Service for their corresponding productId */
        // extracting the product List from cart items list.
        List<Integer> productList = cartItems.stream()
                                    .map(CartItemDto :: getProductId)
                                    .toList();

        // getting the priceList from Pricing team.
        PricingResponseDto priceList = pricingServiceClient.getPricingList(productList);

        // Now I will map the productId with their corresponding prices to know which price is of which product.
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



        /*Step 5 ->We have to create this no external service required , Work is -: Generate Invoice by calculating
         * the price for each products accordance to their quantity and the Total Amount to be paid by adding all
         * the products prices  */ // do not do the step 5 here , create a new service.
        // THIS STEP 5 WILL NOT BE PROCESSED HERE WE WILL CALL A INVOICE_SERVICE TO GENERATE INVOICE AFTER ORDER COMPLETION.



        /*Step 6 -> Provide the Invoice and Total Amount to Payment Service and awaits for response SUCCESS/FAILURE  */

        Boolean success = paymentServiceClient.processPayment(totalPrice);

        if(!success){
            throw new PaymentFailureException("PAYMENT FAILED!!");
        }


        /* Step 7-> Order successfully created now create(save) the order in the database */
        Order order = new Order();
        order.setUserId(orderRequestDto.getUserId());
        order.setTotalAmount(totalPrice);
        order.setOrderStatus(Status.CREATED);

        orderRepo.save(order);

        // Call Invoice Service here by sending the Order.

        OrderResponseDto orderResponseDto = modelMapper.map(order , OrderResponseDto.class);

        /*Step 8-> Notify services like: Inventory , User , Delivery, WishList ,etc.
         about the Order being successfully created. */

        return orderResponseDto;
    }


    public Optional<String> compareInventoryItems(List<CartItemDto> cartList , List<InventoryItemDto> inventoryList){

        // here we are considering that both the list is of same size because the inventory team will only return
        // those products that are in cart.*/

        if(inventoryList.size() < cartList.size()){
            return Optional.of("some items are missing in inventory");
        }


        for(int i=0;i<cartList.size();i++){

            int cartQuantity = cartList.get(i).getQuantity();
            int inventoryQuantity = inventoryList.get(i).getQuantity();

            if(inventoryQuantity < cartQuantity) {
                // here think how we can return that product whose quantity is less than the required quantity -> handled.
                return Optional.of(cartList.get(i).productId.toString());
            }

        }

        return Optional.empty();
    }

    public OrderResponseDto viewOrderDetails(Integer orderId) {
        boolean check = orderRepo.existsById(orderId);
        if(!check){
            throw new OrderNotFoundException("Order with the orderId :" + orderId + " does not exist");
        }

        Optional<Order> order = orderRepo.findByOrderId(orderId);

        return modelMapper.map(order.get() , OrderResponseDto.class);

    }

    public OrderResponseDto cancelOrder(Integer orderId) {

        Optional<Order> orderOptional = orderRepo.findByOrderId(orderId);

        if(orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order with the orderId :" + orderId + " does not exist");
        }

        Order order = orderOptional.get();
        order.setOrderStatus(Status.CANCELLED);

        // calling Inventory Team for order being cancelled.

        return modelMapper.map(order , OrderResponseDto.class);

    }
}
