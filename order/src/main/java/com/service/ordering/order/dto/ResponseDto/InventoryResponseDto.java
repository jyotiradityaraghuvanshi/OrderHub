package com.service.ordering.order.dto.ResponseDto;


import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {

    Integer productId;

    Integer quantity;

    Boolean isAvail;

}
