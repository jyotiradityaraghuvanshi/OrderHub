package com.service.ordering.order.dto.ResponseDto;


import com.service.ordering.order.dto.InventoryMerchantDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMerchantResponseDto {

    public List<InventoryMerchantDto> merchantProductList;

}
