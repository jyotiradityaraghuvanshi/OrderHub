package com.service.ordering.order.dto.ResponseDto;


import com.service.ordering.order.dto.CartItem;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {

    public List<CartItem> cartItems;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItemDtoList(List<CartItem> cartItemDtoList) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItemDtoList(){
        return cartItems;
    }
}
