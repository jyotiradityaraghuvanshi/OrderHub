package com.service.ordering.order.service;

import com.service.ordering.order.dto.RequestDto.InvoiceRequestDto;
import com.service.ordering.order.dto.ResponseDto.InvoiceItemDto;
import com.service.ordering.order.dto.ResponseDto.InvoiceResponseDto;
import com.service.ordering.order.entity.Invoice;
import com.service.ordering.order.entity.Order;
import com.service.ordering.order.entity.OrderItem;
import com.service.ordering.order.exception.OrderNotFoundException;
import com.service.ordering.order.repository.InvoiceRepository;
import com.service.ordering.order.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrderRepo orderRepository;



    public InvoiceResponseDto generateInvoice(InvoiceRequestDto invoiceRequestDto){


        // 1. Fetch order details using orderID
        Integer orderId = invoiceRequestDto.getOrderId();

        // 1.1 if orderId does not exist throw global exception | orderId not found
        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderId);

        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }

        Order order = optionalOrder.get();

        // 2. fetch details from order: userId userEmail priceMap totalAmount
        Integer userId = order.getUserId();
        String userMail = order.getUserMail();
        Integer totalAmount = order.getTotalAmount();
        List<OrderItem> orderItems = order.getOrderItemsList();

        // 5. Map orderItem to invoiceItem
        List<InvoiceItemDto> invoiceItems = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            InvoiceItemDto invoiceItem = new InvoiceItemDto();
            invoiceItem.setProductId(orderItem.getProductId());
            invoiceItem.setQuantity(orderItem.getQuantity());
            invoiceItem.setPrice(orderItem.getPricePerItem()*orderItem.getQuantity());
            invoiceItem.setMerchantId(orderItem.getMerchantId());
        }

        // 6. Create and save Invoice
        Invoice invoice = new Invoice();
        invoice.setGeneratedAt(LocalDateTime.now());
        //invoice.setOrder(order);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        // 7. Map saved Invoice to InvoiceResponseDto
        InvoiceResponseDto responseDto = new InvoiceResponseDto();
        responseDto.setUserId(userId);
        responseDto.setUserMail(userMail);
        responseDto.setTotalAmount(totalAmount);
        responseDto.setItems(invoiceItems);


        // 8 return mapToResponseDto(savedInvoice)
        return responseDto;
    }
}
