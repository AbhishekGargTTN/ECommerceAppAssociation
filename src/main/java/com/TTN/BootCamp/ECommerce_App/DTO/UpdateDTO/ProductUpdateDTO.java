package com.TTN.BootCamp.ECommerce_App.DTO.UpdateDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateDTO {

    private String name;

    private String description;

    private boolean isCancellable;

    private boolean isReturnable;
}
