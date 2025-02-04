package com.service.ordering.order.dto.ResponseDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityResponseDto {

    Integer userId;

    String userName;

    String location;

    Boolean userIsValid;

}
