package com.service.ordering.orders.dto.RequestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private int userId;
    private int cartId;

    // Getters and Setters
}
