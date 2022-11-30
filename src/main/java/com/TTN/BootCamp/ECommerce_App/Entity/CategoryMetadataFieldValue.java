package com.TTN.BootCamp.ECommerce_App.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CategoryMetadataFieldValue {

    @EmbeddedId
    private CategoryMetadataFieldKey categoryMetadataFieldKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("category_id")
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("category_metadata_field_id")
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetadataField categoryMetadataField;

    private String value;

}
