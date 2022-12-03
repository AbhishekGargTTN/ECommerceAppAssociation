package com.TTN.BootCamp.ECommerce_App.Entity;


import com.TTN.BootCamp.ECommerce_App.DTO.Auditing.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Seller extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_gen")
    @SequenceGenerator(name="seller_gen", sequenceName = "seller_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Column(name = "GST")
    private String gst;

    @Column(name = "Company_Contact")
    private String companyContact;

    @Column(name = "Company_Name")
    private String companyName;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "seller",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private Address address;
}
