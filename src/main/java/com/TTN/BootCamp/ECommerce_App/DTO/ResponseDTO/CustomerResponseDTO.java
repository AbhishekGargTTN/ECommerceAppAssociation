package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDTO {

    private long id;
    private String fullName;
    private String email;
    private boolean isActive;
}
