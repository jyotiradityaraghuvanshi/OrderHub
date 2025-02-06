package com.service.ordering.order.dto.ResponseDto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {
    private Integer invoiceId;
    private String userName;
    private List<InvoiceItem> items;
    private Integer totalAmount;

    // Getters and Setters
}
