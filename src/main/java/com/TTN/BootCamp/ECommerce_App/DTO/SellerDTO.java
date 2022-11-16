package com.TTN.BootCamp.ECommerce_App.DTO;

import lombok.Data;

@Data
public class SellerDTO extends UserDTO{

    private String companyName;

    private String gst;

    private long companyContact;

    private AddressDTO address;
}
