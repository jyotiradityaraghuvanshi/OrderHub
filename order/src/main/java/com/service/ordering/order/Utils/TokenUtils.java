package com.service.ordering.order.Utils;

import com.service.ordering.order.dto.ResponseDto.IdentityResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class TokenUtils {

    public static IdentityResponseDto decodeToken(String token){

        Claims claims = Jwts.parserBuilder().build().parseClaimsJwt(token).getBody();

        IdentityResponseDto identityResponseDto = new IdentityResponseDto();

        // dummy response format
        identityResponseDto.setUserId((Integer) claims.get("userId"));
        identityResponseDto.setEmail((String) claims.get("email"));
        identityResponseDto.setUserName((String) claims.get("userName"));
        identityResponseDto.setLocation((String) claims.get("location"));

        // Identity service format
//        identityResponseDto.setUserId((Integer) claims.get("id"));
//        identityResponseDto.setUserName((String) claims.get("name"));
//        identityResponseDto.setEmail((String) claims.get("email"));
//        identityResponseDto.setPhone((String) claims.get("phone"));

        return identityResponseDto;
    }

}
