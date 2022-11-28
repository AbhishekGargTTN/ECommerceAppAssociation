package com.TTN.BootCamp.ECommerce_App.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMetaDataCompositeKey implements Serializable {

    @Column(name = "Category_ID")
    private long categoryId;

    @Column(name = "Category_Meta_Data_Field")
    private long categoryMetaDataFieldId;
}
