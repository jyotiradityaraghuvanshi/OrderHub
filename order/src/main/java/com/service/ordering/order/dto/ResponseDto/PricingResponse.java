package com.service.ordering.order.dto.ResponseDto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingResponse {
    public List<PriceDetail> priceDetailList;

    public List<PriceDetail> getPriceDetails() {
        return priceDetailList;
    }
}
