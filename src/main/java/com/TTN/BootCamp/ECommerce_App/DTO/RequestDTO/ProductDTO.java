package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private String name;

    private String description;

    private String brand;

    private boolean isCancellable;

    private boolean isReturnable;

    private boolean isActive;

    private Long categoryId;

}
