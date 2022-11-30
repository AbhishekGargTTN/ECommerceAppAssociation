package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;


import lombok.Data;

import java.util.List;


@Data
public class MetaFieldValueDTO {
    Long categoryId;
    Long metadataId;
    List<String> values;
}
