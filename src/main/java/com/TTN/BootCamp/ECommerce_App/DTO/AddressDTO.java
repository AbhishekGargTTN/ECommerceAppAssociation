package com.TTN.BootCamp.ECommerce_App.DTO;

import lombok.Data;

@Data
public class AddressDTO {

    private String city;

    private String state;

    private String country;

    private String addressLine;

    private long zipCode;

    private String label;

}
