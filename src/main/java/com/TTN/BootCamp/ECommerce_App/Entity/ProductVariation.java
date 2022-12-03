package com.TTN.BootCamp.ECommerce_App.Entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "Quantity_Available")
    private Long quantityAvailable;

    @Column(name = "Price")
    private Double price;

//    @Column(name = "Primary_Image_Name")
//    String primaryImageName;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Object metaData;

    @Column(name = "Is_Active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "Product_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
}
