package com.service.ordering.order.service;


import com.service.ordering.order.dto.CartItem;
import com.service.ordering.order.dto.RequestDto.PricingRequest;
import com.service.ordering.order.dto.RequestDto.Product;
import com.service.ordering.order.dto.ResponseDto.*;
import com.service.ordering.order.entity.Invoice;
import com.service.ordering.order.entity.Order;
import com.service.ordering.order.exception.ServiceUnavailableException;
import com.service.ordering.order.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    // review again to handle error and simplify the flow and logic
    @Autowired
    private InvoiceServiceClient invoiceServiceClient;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceResponse generateInvoice(Order order) throws ServiceUnavailableException {
        IdentityResponseDto userDetails;
        CartResponseDto cartResponse;
        PricingResponse pricingResponse;

        try {
            // Step 1: Get username from Identity Service
            userDetails = invoiceServiceClient.getUserDetails(order.getUserId());
        }catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceUnavailableException("Error calling IdentityService", e);
        }

        try{
            // Step 2: Get cart details from Wishlist Service
            cartResponse = invoiceServiceClient.getCartDetails(order.getUserId());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceUnavailableException("Error calling CartService", e);
        }

        // Step 3: Get product prices from Pricing Service
        // 3.1 Fetch cart items from cart pricingResponse
        List<Integer> productIds = new ArrayList<>();
        for (CartItem item : cartResponse.getCartItemDtoList()) {
            productIds.add(item.getProductId());
        }

        // 3.2 Convert cartItem type into Product type
        Product product;
        PricingRequest productPricingRequest = new PricingRequest();
        List<Product> productList = new ArrayList<>();
        for(Integer productId : productIds){
            product = new Product();
            product.setProductId(productId);
            productList.add(product);
        }

        // 3.3  a product list to get product prices
        productPricingRequest.setProductsList(productList);

        // 3.4 calling pricing service to get pricingResponse of prices
        try{
            pricingResponse = invoiceServiceClient.getProductPrices(productPricingRequest);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceUnavailableException("Error calling Pricing Service", e);
        }

        List<PriceDetail> priceDetails = pricingResponse.getPriceDetails();

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        List<CartItem> cartItems = cartResponse.getCartItems();

        // Step 4: Convert into invoiceItem
        for(int i = 0; i < priceDetails.size(); i++){
            // review again to handle error and simplify
            long productPrice = priceDetails.get(i).price.longValue();
            long productQuantity = cartItems.get(i).quantity.longValue();
            long productPriceByQuantity = productPrice * productQuantity;

            InvoiceItem invoiceItems = new InvoiceItem();
            invoiceItems.setProductId(priceDetails.get(i).getProductId());
            invoiceItems.setQuantity(Long.valueOf(productQuantity));

            invoiceItems.setPrice(Long.valueOf(productPriceByQuantity));
            invoiceItemList.add(invoiceItems);
        }

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setGeneratedAt(LocalDateTime.now());
        invoiceRepository.save(invoice);

        // Step 5: Create and return the invoice pricingResponse
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setInvoiceId(invoice.getInvoiceId());
        invoiceResponse.setUserName(userDetails.userName);
        invoiceResponse.setItems(invoiceItemList);
        invoiceResponse.setTotalAmount(order.getTotalAmount());

        return invoiceResponse;
    }
}
