package com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SellerUpdateDTO extends UserUpdateDTO {

    @Size(max=30, message = "Enter a valid company name.")
    private String companyName;

    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
            message = "Enter a valid GST number")
    private String gst;

    @Pattern(regexp = "^\\d{10}$", message = "Enter a valid ten-digit contact number.")
    private String companyContact;

    @Valid
    private AddressUpdateDTO address;
}
