package com.service.ordering.order.exception;

public class CartEmptyException extends Exception{

    public CartEmptyException(String message){
        super(message);
    }

}
