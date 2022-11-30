package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;

import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class SellerCategoryResponseDTO {
    private Long id;
    private String name;
    private Category parent;
    private List<MetadataResponseDTO> metadata;
}
