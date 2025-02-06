package com.service.ordering.order.dto.ResponseDto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItem {
    private Integer productId;
    private Long quantity;
    private Long price;

    // Getters and Setters
}
