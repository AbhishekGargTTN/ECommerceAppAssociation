package com.TTN.BootCamp.ECommerce_App.DTO.ResponseDTO;

import lombok.Data;

@Data
public class MetadataResponseDTO {
    private Long metadataId;
    private String fieldName;
    private String possibleValues;
}