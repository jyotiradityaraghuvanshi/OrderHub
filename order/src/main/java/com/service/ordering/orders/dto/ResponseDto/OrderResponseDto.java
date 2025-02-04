package com.service.ordering.orders.dto.ResponseDto;


import com.service.ordering.orders.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private int orderId;
    private int totalAmount;
    private Status orderStatus;

    // Getters and Setters
}
