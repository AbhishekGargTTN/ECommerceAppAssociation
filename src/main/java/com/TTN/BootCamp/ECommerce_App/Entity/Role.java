package com.TTN.BootCamp.ECommerce_App.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "Role")
    private String role;
//
//    public Role() {
//    }
//
//    public Role(long id, String role) {
//        this.id = id;
//        this.role = role;
//
//    }//        @JoinColumn(name = "User_Id")
//    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
//    private Set<User> users;

//    @OneToOne
//    @JoinTable(name = "User_Role"
//            ,joinColumns = @JoinColumn(name = "User_id", referencedColumnName = "id")
//            ,inverseJoinColumns = @JoinColumn(name = "Role_id", referencedColumnName = "id"))
//    private Customer customer;
}
