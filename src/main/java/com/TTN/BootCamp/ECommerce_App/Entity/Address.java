package com.TTN.BootCamp.ECommerce_App.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "City")
    private String city;

    @Column(name = "State")
    private String state;

    @Column(name = "Country")
    private String country;

    @Column(name = "Address_Line")
    private String addressLine;

    @Column(name = "Zip_Code")
    private long zipCode;

    @Column(name = "Label")
    private String label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Customer_ID")
    @JsonBackReference
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Seller_ID")
    @JsonBackReference
    private Seller seller;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", zipCode=" + zipCode +
                ", label='" + label + '\'' +
                ", customer=" + customer +
                ", seller=" + seller +
                '}';
    }
}
