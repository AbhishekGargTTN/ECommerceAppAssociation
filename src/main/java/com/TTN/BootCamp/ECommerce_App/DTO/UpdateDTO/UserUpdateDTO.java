package com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
public class UserUpdateDTO {

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}"
            ,flags = Pattern.Flag.CASE_INSENSITIVE, message = "Enter a valid Email Address")
    private String email;

    @Size(min = 2, max = 30, message = "Must be between 2-30 characters.")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "Invalid Input. " +
            "Name can only contain alphabets.")
    private String firstName;

    @Pattern(regexp="(^[A-Za-z]*$).{0,30}",message = "Invalid Input. " +
            "Middle name can only contain alphabets. Must be between 1-30 characters.")
    private String middleName;

    @Size(min = 2, max = 30, message = "Must be between 2-30 characters.")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "Invalid Input. " +
            "Name can only contain alphabets.")
    private String lastName;

    private boolean isActive;

}





