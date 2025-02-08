package com.service.ordering.order.dto.ResponseDto;


import com.service.ordering.order.dto.InventoryItemDto;
import com.service.ordering.order.dto.InventoryMerchantDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDto {

    public List<InventoryItemDto> inventoryItemList;

    public List<InventoryMerchantDto> merchantList;

    public List<InventoryMerchantDto> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(List<InventoryMerchantDto> merchantList) {
        this.merchantList = merchantList;
    }

    public List<InventoryItemDto> getInventoryItemList() {
        return inventoryItemList;
    }

    public void setInventoryItemList(List<InventoryItemDto> inventoryItemList) {
        this.inventoryItemList = inventoryItemList;
    }
}
