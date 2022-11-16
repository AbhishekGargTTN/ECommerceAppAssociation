package com.TTN.BootCamp.ECommerce_App.Entity;

import lombok.Data;

@Data
public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
}
