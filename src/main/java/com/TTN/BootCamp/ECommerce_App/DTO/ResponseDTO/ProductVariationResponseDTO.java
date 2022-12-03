package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariationResponseDTO {

    private  Long id;
    private Long productId;

    private Object metadata;

    //    private MultipartFile image;
    private Long quantityAvailable;

    private Double price;
}
