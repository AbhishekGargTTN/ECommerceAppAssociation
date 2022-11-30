package com.TTN.BootCamp.ECommerce_App.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class
CategoryMetaDataFieldValues  {

    @EmbeddedId
    private CategoryMetaDataCompositeKey categoryMetaDataCompositeKey;

    String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("Category_ID")
    @JoinColumn(name = "Category_ID")
//    @JsonBackReference
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("Category_Meta_Data_Field_ID")
    @JoinColumn(name = "Category_Meta_Data_Field_ID")
//    @JsonBackReference
    private CategoryMetaDataField categoryMetaDataField;

//    public CategoryMetaDataFieldValues(CategoryMetaDataCompositeKey categoryMetaDataCompositeKey, Category category, CategoryMetaDataField categoryMetaDataField) {
//        this.categoryMetaDataCompositeKey = categoryMetaDataCompositeKey;
//        this.category = category;
//        this.categoryMetaDataField = categoryMetaDataField;
//    }

}
