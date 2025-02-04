package com.service.ordering.orders.service;

import com.service.ordering.orders.clients.IdentityServiceClient;
import com.service.ordering.orders.clients.PricingServiceClient;
import com.service.ordering.orders.clients.WishlistServiceClient;
import com.service.ordering.orders.dto.RequestDto.CartRequest;
import com.service.ordering.orders.dto.ResponseDto.CartItem;
import com.service.ordering.orders.dto.ResponseDto.CartResponse;
import com.service.ordering.orders.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceService {

    @Autowired
    private IdentityServiceClient identityServiceClient;

    @Autowired
    private WishlistServiceClient wishlistServiceClient;

    @Autowired
    private PricingServiceClient pricingServiceClient;

    // update later
    public String[] generateInvoice(Order order){
        // Get Username
        String userName =  identityServiceClient.getUserName(String.valueOf(order.getUserId()));

        // Get Product details: we can call product and supply service to get product name
        CartResponse cartResponse = wishlistServiceClient.getCartDetails(new CartRequest(order.getUserId(), order.getCartId()));
        List<CartItem> items = cartResponse.getItems();
        int itemsSize = items.size();
        String[] invoice = new String[itemsSize+2];
        invoice[0] = userName;

        for(int i = 0; i < items.size()-1; i++){
            CartItem item = items.get(i);
            BigDecimal price = pricingServiceClient.getProductPrice(String.valueOf(item.getProductId()));
            String productId = String.valueOf(item.getProductId());
            String quantity = String.valueOf(item.getQuantity());

            StringBuilder productInfo = new StringBuilder(productId + " " + quantity + " : " + price);
            invoice[i+1] = productInfo.toString();
        }

        invoice[invoice.length-1] = String.valueOf(order.getTotalAmount());

        return invoice;
    }
}
