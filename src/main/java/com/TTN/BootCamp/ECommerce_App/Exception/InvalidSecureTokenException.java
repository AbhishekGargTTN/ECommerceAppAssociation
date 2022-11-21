package com.TTN.BootCamp.ECommerce_App.Exception;

public class InvalidSecureTokenException extends RuntimeException{
    public InvalidSecureTokenException(String message) {
        super(message);
    }
}
