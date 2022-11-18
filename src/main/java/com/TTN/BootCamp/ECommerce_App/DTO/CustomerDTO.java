package com.TTN.BootCamp.ECommerce_App.DTO;

import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerDTO extends UserDTO{

    private long contact;

    private List<AddressDTO> addresses= new ArrayList<>();
}
