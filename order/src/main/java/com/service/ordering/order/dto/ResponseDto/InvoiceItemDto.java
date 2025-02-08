package com.service.ordering.order.dto.ResponseDto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItemDto {
    private Integer productId;
    private Integer quantity;
    private Integer price;
    private Integer merchantId;

    // Getters and Setters
}
