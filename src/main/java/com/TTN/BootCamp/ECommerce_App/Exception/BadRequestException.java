package com.TTN.BootCamp.ECommerce_App.Exception;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String errorMessage){
        super(errorMessage);
    }
}
