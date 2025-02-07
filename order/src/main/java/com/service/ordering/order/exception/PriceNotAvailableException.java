package com.service.ordering.order.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PriceNotAvailableException extends RuntimeException{

    public PriceNotAvailableException(String message){
        super(message);
    }

}
