package com.service.ordering.order.service;

import com.service.ordering.order.dto.RequestDto.InvoiceRequestDto;
import com.service.ordering.order.dto.ResponseDto.InvoiceItemDto;
import com.service.ordering.order.dto.ResponseDto.InvoiceResponseDto;
import com.service.ordering.order.entity.Invoice;
import com.service.ordering.order.entity.Order;
import com.service.ordering.order.entity.OrderItem;
import com.service.ordering.order.exception.InvalidInputException;
import com.service.ordering.order.exception.InvoiceAlreadyExistsException;
import com.service.ordering.order.exception.OrderNotFoundException;
import com.service.ordering.order.repository.InvoiceRepository;
import com.service.ordering.order.repository.OrderRepo;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;



    public InvoiceResponseDto generateInvoice(InvoiceRequestDto invoiceRequestDto){


        // 1. Fetch order details using orderID
        Integer orderId = invoiceRequestDto.getOrderId();

        // 1.1 if orderId does not exist throw global exception | orderId not found
        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderId);

        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }

        Order order = optionalOrder.get();

        Optional<Invoice> existingInvoice = invoiceRepository.findByOrder(order);
        if (existingInvoice.isPresent()) {
            throw new InvoiceAlreadyExistsException("Invoice already generated for Order ID: " + orderId);
        }

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

            invoiceItems.add(invoiceItem);
        }

        // 6. Create and save Invoice
        Invoice invoice = new Invoice();
        invoice.setGeneratedAt(LocalDateTime.now());
        invoice.setOrder(order);
        invoice.setUserId(userId);
        invoice.setTotalAmount(totalAmount);
        invoice.setUserMail(userMail);

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

    public InvoiceResponseDto getInvoiceDetails(Integer invoiceId) {
        Optional<Invoice> existingInvoice = invoiceRepository.findById(invoiceId);
        if(existingInvoice.isEmpty()){
            throw new InvalidInputException("Invoice doesn't exist with id :" + invoiceId);
        }

        Invoice invoice = existingInvoice.get();


        // map order items in list to show response to userhm

        List<InvoiceItemDto> invoiceItems = new ArrayList<>();
        for (OrderItem orderItem : invoice.getOrder().getOrderItemsList()) {
            InvoiceItemDto invoiceItem = new InvoiceItemDto();
            invoiceItem.setProductId(orderItem.getProductId());
            invoiceItem.setQuantity(orderItem.getQuantity());
            invoiceItem.setPrice(orderItem.getPricePerItem() * orderItem.getQuantity());
            invoiceItem.setMerchantId(orderItem.getMerchantId());
            invoiceItems.add(invoiceItem);
        }

        // Manually map invoice fields
        InvoiceResponseDto responseDto = new InvoiceResponseDto();
        responseDto.setUserId(invoice.getUserId());
        responseDto.setUserMail(invoice.getUserMail());
        responseDto.setTotalAmount(invoice.getTotalAmount());
        responseDto.setItems(invoiceItems); // ✅ Add invoice items

        return responseDto;

    }
}
