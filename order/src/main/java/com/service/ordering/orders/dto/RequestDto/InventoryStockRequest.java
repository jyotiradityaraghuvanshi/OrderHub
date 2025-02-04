package com.service.ordering.orders.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryStockRequest {
    private String productId;
    private String quantity;

    // Getters and Setters
}
