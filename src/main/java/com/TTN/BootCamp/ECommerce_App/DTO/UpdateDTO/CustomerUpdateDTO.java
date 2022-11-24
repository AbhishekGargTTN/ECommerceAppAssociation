package com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CustomerUpdateDTO extends UserUpdateDTO {


    @Pattern(regexp = "^\\d{10}$", message = "Enter a valid ten-digit contact number.")
    private String contact;

}
