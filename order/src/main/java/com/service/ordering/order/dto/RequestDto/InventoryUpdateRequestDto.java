package com.service.ordering.order.dto.RequestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateRequestDto {

    public Integer productId;

    public Integer quantity;

    public Integer merchantId;

//    public InventoryUpdateRequestDto(Integer productId, Integer quantity, Integer merchantId) {
//        this.productId = productId;
//        this.quantity = quantity;
//        this.merchantId = merchantId;
//    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
}
