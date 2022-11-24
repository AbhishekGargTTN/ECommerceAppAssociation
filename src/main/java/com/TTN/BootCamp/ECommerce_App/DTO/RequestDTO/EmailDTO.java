package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class EmailDTO {


    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}"
            ,flags = Pattern.Flag.CASE_INSENSITIVE, message = "Enter a valid Email Address")
    private String email;
}
