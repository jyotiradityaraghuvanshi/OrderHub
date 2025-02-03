package com.service.ordering.order.dto.ResponseDto;


import com.service.ordering.order.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    Integer orderId;

    Status orderStatus;

    Integer totalAmount;

}
