package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;

import lombok.Data;

@Data
public class MetaFieldValueResponseDTO {
    private Long categoryId;
    private Long metaFieldId;
    private String values;
}
