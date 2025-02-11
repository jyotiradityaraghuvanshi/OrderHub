package com.service.ordering.order.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class InvoiceAlreadyExistsException extends RuntimeException{

    public InvoiceAlreadyExistsException(String message){
        super(message);
    }

}
