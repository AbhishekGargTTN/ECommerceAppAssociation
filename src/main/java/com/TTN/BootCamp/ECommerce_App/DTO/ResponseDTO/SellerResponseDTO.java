package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;

import com.TTN.BootCamp.ECommerce_App.Entity.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SellerResponseDTO {

    private long id;
    private String fullName;
    private String email;
    private boolean isActive;
    private String companyName;
    private String gst;
    private String companyContact;
    private Address address;
    private byte[] image;
}
