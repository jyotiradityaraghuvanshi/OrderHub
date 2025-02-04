package com.service.ordering.order.exception;

public class ProductOutOfStockException extends Exception{

    public ProductOutOfStockException(String message){
        super(message);
    }
}
