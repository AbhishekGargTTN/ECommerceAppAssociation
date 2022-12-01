package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;

import com.TTN.BootCamp.ECommerce_App.Entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String brand;

    private boolean isCancellable;

    private boolean isReturnable;

    private boolean isActive;

    private Category category;

}
