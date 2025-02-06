package com.service.ordering.order.dto.RequestDto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingRequest {
    public List<Product> productsList;
}
