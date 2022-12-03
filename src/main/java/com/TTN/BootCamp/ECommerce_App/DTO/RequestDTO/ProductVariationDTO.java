package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
public class ProductVariationDTO {

    private Long productId;

    private Map<String, Set<String>> metadata;

    //    private MultipartFile image;
    private Long quantityAvailable;

    private Double price;
}
