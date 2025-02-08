package com.service.ordering.order.dto.ResponseDto;


import com.service.ordering.order.dto.CartItemDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartListResponseDto {

    public List<CartItemDto> cartItemDtoList;

    public List<CartItemDto> getCartItemDtoList() {
        return cartItemDtoList;
    }

    public void setCartItemDtoList(List<CartItemDto> cartItemDtoList) {
        this.cartItemDtoList = cartItemDtoList;
    }
}
