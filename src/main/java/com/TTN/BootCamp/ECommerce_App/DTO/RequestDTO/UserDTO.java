package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
public class UserDTO {

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}"
            ,flags = Pattern.Flag.CASE_INSENSITIVE, message = "Enter a valid Email Address")
    private String email;

    @NotEmpty(message = "First Name is a mandatory field.")
    @Size(min = 2, max = 30, message = "Must be between 2-30 characters.")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "Invalid Input. " +
            "Name can only contain alphabets.")
    private String firstName;

    @Pattern(regexp="(^[A-Za-z]*$).{0,30}",message = "Invalid Input. " +
            "Middle name can only contain alphabets. Must be between 1-30 characters.")
    private String middleName;

    @NotEmpty(message = "Last Name is a mandatory field.")
    @Size(min = 2, max = 30, message = "Must be between 2-30 characters.")
    @Pattern(regexp="(^[A-Za-z]*$)",message = "Invalid Input. " +
            "Name can only contain alphabets.")
    private String lastName;

    @NotEmpty(message = "Password is a mandatory field.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,15}",
            message = "Enter a valid password. Password must be between 8-15 characters " +
                    "and contains at least 1 lower case, 1 upper case, 1 special character and 1 number.")
    @JsonIgnore
    private String password;

    @NotEmpty(message = "Confirm Password is a mandatory field.")
    @JsonIgnore
    private String confirmPassword;

    private boolean isActive;

}





