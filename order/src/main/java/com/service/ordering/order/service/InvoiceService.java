package com.service.ordering.order.service;

import com.service.ordering.order.dto.RequestDto.InvoiceRequestDto;
import com.service.ordering.order.dto.ResponseDto.InvoiceResponseDto;
import com.service.ordering.order.repository.InvoiceRepository;
import com.service.ordering.order.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrderRepo orderRepository;


    public InvoiceResponseDto generateInvoice(InvoiceRequestDto invoiceRequestDto){
        // 1. Fetch order details using orderID
        // 1.1 if orderId does not exist throw global exception | orderId not found

        // 2. fetch details from order: userId userEmail priceMap totalAmount

        // 5. Map cartItem to invoiceItem

        // 6. Create and save Invoice

        // 7. Map saved Invoice to InvoiceResponseDto

        // 8 return mapToResponseDto(savedInvoice)

        return null;
    }
}
