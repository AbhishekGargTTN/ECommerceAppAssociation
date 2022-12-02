package com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO;

import com.TTN.BootCamp.ECommerce_App.Entity.MetaData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariationUpdateDTO {


    private MetaData metadata;

    //    private MultipartFile image;
    private long quantityAvailable;

    private double price;
    private boolean isActive;
}
