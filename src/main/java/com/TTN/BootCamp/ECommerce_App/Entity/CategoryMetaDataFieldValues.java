package com.TTN.BootCamp.ECommerce_App.Entity;

import com.TTN.BootCamp.ECommerce_App.DTO.Auditing.Auditable;
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
CategoryMetaDataFieldValues extends Auditable<String> {

    @EmbeddedId
    private CategoryMetaDataCompositeKey categoryMetaDataCompositeKey;

    String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("Category_ID")
    @JoinColumn(name = "Category_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("Category_Meta_Data_Field_ID")
    @JoinColumn(name = "Category_Meta_Data_Field_ID")
    private CategoryMetaDataField categoryMetaDataField;



}
