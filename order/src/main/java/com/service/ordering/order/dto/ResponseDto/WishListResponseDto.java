package com.service.ordering.order.dto.ResponseDto;


import com.service.ordering.order.dto.CartItemDto;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishListResponseDto {

    public List<CartItemDto> cartItemDtoList;

    public List<CartItemDto> getCartItemDtoList() {
        return cartItemDtoList;
    }

    public void setCartItemDtoList(List<CartItemDto> cartItemDtoList) {
        this.cartItemDtoList = cartItemDtoList;
    }
}
