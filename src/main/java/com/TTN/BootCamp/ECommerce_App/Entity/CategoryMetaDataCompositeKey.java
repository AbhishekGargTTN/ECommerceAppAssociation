package com.TTN.BootCamp.ECommerce_App.Entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMetaDataCompositeKey implements Serializable {

    @Column(name = "Category_id")
    private long categoryId;

    @Column(name = "Category_Meta_Data_Field_ID")
    private long categoryMetaDataFieldId;

}
