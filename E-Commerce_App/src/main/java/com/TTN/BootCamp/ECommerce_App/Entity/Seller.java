package com.TTN.BootCamp.ECommerce_App.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
//@PrimaryKeyJoinColumn(name = "User_ID")
public class Seller {//extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "GST")
    private String gst;

    @Column(name = "Company_Contact")
    private long companyContact;

    @Column(name = "Company_Name")
    private String companyName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "User_Id")
    private User user;

    @OneToOne(mappedBy = "seller",cascade = CascadeType.ALL)
    private Address address;
}
