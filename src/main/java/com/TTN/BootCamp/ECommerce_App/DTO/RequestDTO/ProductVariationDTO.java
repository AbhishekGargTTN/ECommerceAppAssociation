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

    private long productId;

    private Map<String, Set<String>> metadata;

    //    private MultipartFile image;
    private long quantityAvailable;

    private double price;
}
