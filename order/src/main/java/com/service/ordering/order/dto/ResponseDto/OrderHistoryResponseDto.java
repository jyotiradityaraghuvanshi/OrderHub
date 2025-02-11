package com.service.ordering.order.dto.ResponseDto;

import com.service.ordering.order.Enum.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryResponseDto {

    private Double totalAmount;

    private String Address;

    private Status OrderStatus;

    private LocalDateTime updatedAt;
}
