package com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO;

import com.TTN.BootCamp.ECommerce_App.DTO.AddressDTO;
import com.TTN.BootCamp.ECommerce_App.DTO.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerUpdateDTO extends UserUpdateDTO {


    @Pattern(regexp = "^\\d{10}$", message = "Enter a valid ten-digit contact number.")
    private String contact;

}
