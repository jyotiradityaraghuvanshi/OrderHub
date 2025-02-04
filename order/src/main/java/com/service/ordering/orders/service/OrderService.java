package com.service.ordering.orders.service;


import com.service.ordering.orders.Enum.Status;
import com.service.ordering.orders.clients.IdentityServiceClient;
import com.service.ordering.orders.clients.InventoryServiceClient;
import com.service.ordering.orders.clients.PricingServiceClient;
import com.service.ordering.orders.clients.WishlistServiceClient;
import com.service.ordering.orders.dto.RequestDto.CartRequest;
import com.service.ordering.orders.dto.RequestDto.InventoryAdjustmentRequest;
import com.service.ordering.orders.dto.RequestDto.InventoryStockRequest;
import com.service.ordering.orders.dto.RequestDto.OrderRequestDto;
import com.service.ordering.orders.dto.ResponseDto.CartItem;
import com.service.ordering.orders.dto.ResponseDto.CartResponse;
import com.service.ordering.orders.dto.ResponseDto.InventoryStockResponse;
import com.service.ordering.orders.entity.Order;
import com.service.ordering.orders.repository.OrderRepo;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.math.BigDecimal;

@Component
@Builder
public class OrderService {

    @Autowired
    private IdentityServiceClient identityServiceClient;

    @Autowired
    private WishlistServiceClient wishlistServiceClient;

    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    private PricingServiceClient pricingServiceClient;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OrderRepo orderRepo;


    public Boolean createOrdering(OrderRequestDto orderRequestDto) {

        // Step 1: Verify user
        boolean isUserValid = identityServiceClient.verifyUser(String.valueOf(orderRequestDto.getUserId()));
        if (!isUserValid) {
            throw new RuntimeException("User is not valid.");
        }

        // Step 2: Fetch cart details
        CartResponse cartResponse = wishlistServiceClient.getCartDetails(new CartRequest(orderRequestDto.getUserId(), orderRequestDto.getCartId()));
        List<CartItem> items = cartResponse.getItems();

        for (CartItem item : items) {
            InventoryStockRequest inventoryStockRequest = new InventoryStockRequest(String.valueOf(item.getProductId()), String.valueOf(item.getQuantity()));
            InventoryStockResponse inventoryStockResponse = inventoryServiceClient.checkStock(inventoryStockRequest);
            if (!inventoryStockResponse.isAvailable()) {
                throw new RuntimeException("Product " + item.getProductId() + " is out of stock.");
            }
        }

        // Step 3: Get product prices
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : items) {
            BigDecimal price = pricingServiceClient.getProductPrice(String.valueOf(item.getProductId()));
            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // Step 4: Generate invoice
        Order order = new Order();
        order.setUserId(orderRequestDto.getUserId());
        order.setCartId(orderRequestDto.getCartId());
        order.setTotalAmount(totalAmount);
        order.setStatus(Status.CREATED);

        // Step 5: Save order
        orderRepo.save(order);

        String[] invoice = invoiceService.generateInvoice(order);

        // implement better logic here later on
        for(String details : invoice){
            System.out.println(details);
        }

        //Step 1-> First check user by the api with Identity team , whether the user is valid or not.
        /* get/UserValidation(orderRequestDto.getUserId()) , if the response is true then proceed further
        * else return Exception -INVALIDUserException */


        /* Step 2-> Fetch List of Products and their quantity from WishList Service , Call the wishList by giving
        * the cartId and catch the List<Items> itemsList which contains productId , Quantity */


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

    public String cancelOrder(int orderId){
        // Step 1: Notify Inventory Service to adjust stock
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        CartResponse cartResponse = wishlistServiceClient.getCartDetails(new CartRequest(order.getUserId(), order.getCartId()));
        List<CartItem> items = cartResponse.getItems();

        // Release reserved stock
        for (CartItem item : items) {
            InventoryAdjustmentRequest inventoryRequest = new InventoryAdjustmentRequest();
            inventoryRequest.setProductId(String.valueOf(item.getProductId()));
            inventoryRequest.setQuantity(String.valueOf(item.getQuantity()));
            inventoryServiceClient.adjustStock(inventoryRequest);
        }

        // Step 2: Change the status of order and save it
        order.setOrderStatus(Status.CANCELLED);
        orderRepo.save(order);

        return "Order Cancelled Successfully!";
    }

    // view order history
    // getInvoice
}
