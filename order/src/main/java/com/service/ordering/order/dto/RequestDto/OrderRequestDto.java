package com.service.ordering.order.dto.RequestDto;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    public Integer userId;

    public Integer cartId;

    public Integer getUserId() {
        return userId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
}
