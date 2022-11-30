package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;


import com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO.ChildCategoryDTO;
import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CategoryResponseDTO {
    private long id;
    private String name;
    private Category parent;
    private Set<ChildCategoryDTO> children = new HashSet<>();
    private List<MetadataResponseDTO> metadataList = new ArrayList<>();

}
