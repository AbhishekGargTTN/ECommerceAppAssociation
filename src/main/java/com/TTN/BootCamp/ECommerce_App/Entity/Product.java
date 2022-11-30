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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Is_Cancellable")
    private boolean isCancellable;

    @Column(name = "Is_Returnable")
    private boolean isReturnable;

    @Column(name = "Is_Active")
    private boolean isActive;

    @Column(name = "Is_Deleted")
    private boolean isDeleted;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "Category_ID")
    @JsonBackReference
    private Category category;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "Seller_User_ID")
    @JsonBackReference
    private Seller seller;
}
