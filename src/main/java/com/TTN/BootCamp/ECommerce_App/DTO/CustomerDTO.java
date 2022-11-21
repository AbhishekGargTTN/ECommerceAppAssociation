package com.TTN.BootCamp.ECommerce_App.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerDTO extends UserDTO{

    @NotEmpty(message = "Contact number is a mandatory field.")
    @Pattern(regexp = "^\\d{10}$", message = "Enter a valid ten-digit contact number.")
    private String contact;

    private List<AddressDTO> addresses= new ArrayList<>();
}
