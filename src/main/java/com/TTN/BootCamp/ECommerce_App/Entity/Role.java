package com.TTN.BootCamp.ECommerce_App.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
    @SequenceGenerator(name="role_gen", sequenceName = "role_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Column(name = "Role")
    private String role;

}
