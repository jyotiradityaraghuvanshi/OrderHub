package com.service.ordering.order.exception;

public class ServiceUnavailableException extends Exception{

    public ServiceUnavailableException(String message, Throwable cause){
        super(message, cause);
    }
}
