package com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO;

import com.TTN.BootCamp.ECommerce_App.Entity.MetaData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductVariationUpdateDTO {


    private Object metadata;

    //    private MultipartFile image;
    private Long quantityAvailable;

    private Double price;
    private boolean isActive;
}
