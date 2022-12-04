package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CustomerDTO extends UserDTO{

    private long id;

    @NotEmpty(message = "Contact number is a mandatory field.")
    @Pattern(regexp = "^\\d{10}$", message = "Enter a valid ten-digit contact number.")
    private String contact;

    @Valid
    private List<AddressDTO> addresses= new ArrayList<>();

    private byte[] image;
}
