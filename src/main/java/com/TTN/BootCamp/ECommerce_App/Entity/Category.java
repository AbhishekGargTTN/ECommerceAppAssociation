package com.TTN.BootCamp.ECommerce_App.Entity;

import com.TTN.BootCamp.ECommerce_App.DTO.Auditing.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_gen")
    @SequenceGenerator(name="category_gen", sequenceName = "category_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    long id;

    @Column(name = "Name")
    String name;

    @OneToOne()
    @JoinColumn(name = "Parent_Category_ID")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Category> subCategories = new HashSet<>();
}
