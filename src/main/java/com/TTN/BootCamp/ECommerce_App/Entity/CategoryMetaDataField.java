package com.TTN.BootCamp.ECommerce_App.Entity;

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
public class CategoryMetaDataField {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metadata_gen")
    @SequenceGenerator(name="metadata_gen", sequenceName = "metadata_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    long id;

    @Column(name = "Name")
    String name;
}
