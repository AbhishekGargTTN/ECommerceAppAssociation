package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;


import lombok.Data;

@Data
public class CategoryDTO {

    private String name;

    private Long parentId;
}
