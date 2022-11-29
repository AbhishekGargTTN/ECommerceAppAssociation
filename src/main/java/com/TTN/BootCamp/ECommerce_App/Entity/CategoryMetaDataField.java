package com.TTN.BootCamp.ECommerce_App.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Data
public class CategoryMetaDataField {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metadata_gen")
    @SequenceGenerator(name="metadata_gen", sequenceName = "metadata_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    long id;

    @Column(name = "Name")
    String name;
}
