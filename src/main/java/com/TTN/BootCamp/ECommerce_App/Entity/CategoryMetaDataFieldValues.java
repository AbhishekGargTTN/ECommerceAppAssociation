package com.TTN.BootCamp.ECommerce_App.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category_metadata_field_values")
public class CategoryMetaDataFieldValues extends CategoryMetaDataField{

    @EmbeddedId
    CategoryMetaDataCompositeKey categoryMetaDataCompositeKey= new CategoryMetaDataCompositeKey();

    @Column(name = "Name")
    String values;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @MapsId("categoryId")
//    @JoinColumn(name = "Category_ID")
//    @JsonBackReference
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @MapsId("categoryMetaDataFieldId")
//    @JoinColumn(name = "Category_Meta_Data_Field")
//    @JsonBackReference
    private CategoryMetaDataField categoryMetaDataField;

    public CategoryMetaDataFieldValues(CategoryMetaDataCompositeKey categoryMetaDataCompositeKey, Category category, CategoryMetaDataField categoryMetaDataField) {
        this.categoryMetaDataCompositeKey = categoryMetaDataCompositeKey;
        this.category = category;
        this.categoryMetaDataField = categoryMetaDataField;
    }
}
