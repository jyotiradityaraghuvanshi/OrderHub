package com.service.ordering.order.dto;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    public Integer productId;

    public Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

// this should be inside ResponseDto folder
