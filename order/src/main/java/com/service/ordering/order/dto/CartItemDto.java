package com.service.ordering.order.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    public Integer productId;

    public Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
