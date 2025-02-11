package com.service.ordering.order.controller;

import com.service.ordering.order.dto.RequestDto.InvoiceRequestDto;
import com.service.ordering.order.dto.ResponseDto.InvoiceResponseDto;
import com.service.ordering.order.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/generate-invoice")
    public ResponseEntity<InvoiceResponseDto> generateInvoice(@RequestBody InvoiceRequestDto invoiceRequestDto){
        InvoiceResponseDto invoiceResponseDto = invoiceService.generateInvoice(invoiceRequestDto);

        return new ResponseEntity<>(invoiceResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get-invoice")
    public ResponseEntity<InvoiceResponseDto> getInvoiceDetails(@RequestParam Integer invoiceId){
        InvoiceResponseDto invoiceResponseDto = invoiceService.getInvoiceDetails(invoiceId);

        return new ResponseEntity<>(invoiceResponseDto , HttpStatus.ACCEPTED);
    }
}
