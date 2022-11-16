package com.TTN.BootCamp.ECommerce_App.DTO;

import lombok.Data;

@Data
public class SellerDTO extends UserDTO{

    private long id;

    private String companyName;

    private String gst;

    private long companyContact;


}
