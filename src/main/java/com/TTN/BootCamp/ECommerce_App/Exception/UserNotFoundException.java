package com.TTN.BootCamp.ECommerce_App.Exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
