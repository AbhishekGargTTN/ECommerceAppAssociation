package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
public class SellerDTO extends UserDTO{

    @NotEmpty(message = "Company Name is a mandatory field.")
    @Size(max=30, message = "Enter a valid company name.")
    private String companyName;

    @NotNull(message = "GST number is mandatory field.")
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
            message = "Enter a valid GST number")
    private String gst;

    @NotNull(message = "Contact number is a mandatory field.")
    @Pattern(regexp = "^\\d{10}$", message = "Enter a valid ten-digit contact number.")
    private String companyContact;

    @Valid
    private AddressDTO address;

    private byte[] image;
}
