package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;

import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    String name;

    private Long parentCategoryId;

}
