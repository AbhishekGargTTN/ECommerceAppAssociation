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
@AllArgsConstructor
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Quantity_Available")
    private Long quantityAvailable;

    @Column(name = "Price")
    private double price;

    @Column(name = "Primary_Image_Name")
    private String primaryImageName;

    @Column(name = "Is_Active")
    private boolean isActive;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "Product_ID")
    @JsonBackReference
    private Product product;
}
