package com.service.ordering.order.dto.ResponseDto;


import com.service.ordering.order.dto.PriceItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingResponseDto {

    public List<PriceItemDto> priceList;

    public List<PriceItemDto> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<PriceItemDto> priceList) {
        this.priceList = priceList;
    }
}
