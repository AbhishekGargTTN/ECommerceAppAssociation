package com.TTN.BootCamp.ECommerce_App.Exception;

public class PasswordDoNotMatchException extends RuntimeException{
    public PasswordDoNotMatchException(String message) {
        super(message);
    }
}
