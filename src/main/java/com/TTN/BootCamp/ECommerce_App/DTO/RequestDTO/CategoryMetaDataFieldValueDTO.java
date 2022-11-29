package com.TTN.BootCamp.ECommerce_App.DTO.RequestDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryMetaDataFieldValueDTO {


    private List<String> values;

    private long categoryId;

    private long metaDataFieldId;

}
