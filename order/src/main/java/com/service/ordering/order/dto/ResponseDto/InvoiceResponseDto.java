package com.service.ordering.order.dto.ResponseDto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponseDto {
    private Integer userId;
    private String userEmail;
    private Integer totalAmount;
    //    private Integer orderId;
    private List<InvoiceItemDto> items;

    // Getters and Setters
}
