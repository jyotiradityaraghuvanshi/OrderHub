package com.service.ordering.order.controller;

import com.service.ordering.order.dto.RequestDto.InvoiceRequestDto;
import com.service.ordering.order.dto.ResponseDto.InvoiceResponseDto;
import com.service.ordering.order.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/generate-invoice/{orderId}")
    public ResponseEntity<InvoiceResponseDto> generateInvoice(@RequestBody InvoiceRequestDto invoiceRequestDto){
        InvoiceResponseDto invoiceResponseDto = invoiceService.generateInvoice(invoiceRequestDto);

        return ResponseEntity.ok(invoiceResponseDto);
    }
}
