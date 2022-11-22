package com.TTN.BootCamp.ECommerce_App.Exception;

public class InActiveAccountException extends RuntimeException{

    public InActiveAccountException(String message) {
        super(message);
    }
}
