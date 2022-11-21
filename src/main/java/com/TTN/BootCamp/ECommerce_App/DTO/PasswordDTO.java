package com.TTN.BootCamp.ECommerce_App.DTO;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PasswordDTO {

    @NotEmpty(message = "Password is a mandatory field.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}",
            message = "Enter a valid password. Password must be between 8-15 characters " +
                    "and contains at least 1 lower case, 1 upper case, 1 special character and 1 number.")
    private String password;

    @NotEmpty(message = "Password is a mandatory field.")
    private String confirmPassword;
}
