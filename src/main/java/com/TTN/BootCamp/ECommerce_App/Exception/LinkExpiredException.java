package com.TTN.BootCamp.ECommerce_App.Exception;

public class LinkExpiredException extends RuntimeException{
    public LinkExpiredException(String message) {
        super(message);
    }
}
