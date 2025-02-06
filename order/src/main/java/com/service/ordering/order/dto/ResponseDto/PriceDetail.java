package com.service.ordering.order.dto.ResponseDto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceDetail {
    public Integer productId;
    public Integer price;
}
