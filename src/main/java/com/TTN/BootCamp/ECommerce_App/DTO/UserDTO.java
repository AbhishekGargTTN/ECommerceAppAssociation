package com.TTN.BootCamp.ECommerce_App.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class UserDTO {

    private long id;

    private String email;

    private String firstName;

    private String middleName;

    private String lastName;

    private String password;

    private long contact;

    private String city;

    private String state;

    private String country;

    private String addressLine;

    private long zipCode;

    private String label;
}
