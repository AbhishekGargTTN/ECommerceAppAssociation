package com.TTN.BootCamp.ECommerce_App.DTO;

import lombok.Data;

@Data
public class CustomerDTO extends UserDTO{

    private long id;

    private long contact;
}
