package com.TTN.BootCamp.ECommerce_App.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "Quantity_Available")
    private long quantityAvailable;

    @Column(name = "Price")
    private double price;

    @Column(name = "Primary_Image_Name")
    String primaryImageName;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private MetaData metaData;

    @Column(name = "Is_Active")
    private boolean isActive;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "Product_ID")
    @JsonBackReference
    private Product product;
}
