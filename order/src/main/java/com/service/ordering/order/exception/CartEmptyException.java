package com.service.ordering.order.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartEmptyException extends RuntimeException{

    public CartEmptyException(String message){
        super(message);
    }

}
