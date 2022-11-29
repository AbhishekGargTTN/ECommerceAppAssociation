package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;

import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CategoryResponseDTO {

    long id;

    String name;

    private Category parentCategory;

    private Set<Category> subCategories = new HashSet<>();

}
