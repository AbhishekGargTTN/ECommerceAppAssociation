package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;

import com.TTN.BootCamp.ECommerce_App.Entity.MetaData;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ProductVariationResponseDTO {

    private  long id;
    private Long productId;

    private MetaData metadata;

    //    private MultipartFile image;
    private long quantityAvailable;

    private double price;
}
