package com.service.ordering.order.dto.ResponseDto;


import com.service.ordering.order.dto.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishListResponseDto {

    Integer cartId;

    List<CartItemDto> cartItemDtoList;

}
