package com.TTN.BootCamp.ECommerce_App.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AddressDTO {

    @NotEmpty(message = "This is a mandatory field.")
    @Pattern(regexp="(^[A-Za-z]*$).{2,30}",message =
            "Can only contain alphabets. City must be between 2-30 characters.")
    private String city;

    @NotEmpty(message = "This is a mandatory field.")
    @Pattern(regexp="(^[A-Za-z]*$).{2,30}",message =
            "Can only contain alphabets. State must be between 2-30 characters.")
    private String state;

    @NotEmpty(message = "This is a mandatory field.")
    @Pattern(regexp="(^[A-Za-z]*$).{2,30}",message =
            "Can only contain alphabets. Country must be between 2-30 characters.")
    private String country;

    @NotEmpty(message = "This is a mandatory field.")
    @Pattern(regexp="(^[A-Za-z0-9/]*$).{2,30}",message =
            "Can only contain alphabets, numbers and '/'. Address must be between 2-30 characters.")
    private String addressLine;

    @NotEmpty(message = "This is a mandatory field.")
    @Pattern(regexp="(^[0-9]*$).{6}",message =
            "Can only contain numbers. Zip Code must have 6 digits.")
    private long zipCode;

    private String label;

}
