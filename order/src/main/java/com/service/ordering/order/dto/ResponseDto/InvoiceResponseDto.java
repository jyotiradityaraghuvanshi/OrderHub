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
    private String userMail;
    private Integer totalAmount;
    private Integer invoiceId;
    private List<InvoiceItemDto> items;
    //    private Integer orderId;

    // Getters and Setters
}
