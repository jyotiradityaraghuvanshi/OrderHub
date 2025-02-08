package com.service.ordering.order.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class PaymentFailureException extends RuntimeException {

    public PaymentFailureException(String message){
        super(message);
    }

}
