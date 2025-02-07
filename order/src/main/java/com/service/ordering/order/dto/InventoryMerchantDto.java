package com.service.ordering.order.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMerchantDto {

    public Integer productId;

    public Integer merchantId;

}
