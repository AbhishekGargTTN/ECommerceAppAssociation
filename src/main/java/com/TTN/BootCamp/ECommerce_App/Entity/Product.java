package com.TTN.BootCamp.ECommerce_App.Entity;

import com.TTN.BootCamp.ECommerce_App.DTO.Auditing.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString
public class Product extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    @SequenceGenerator(name="product_gen", sequenceName = "product_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private long id;

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
    private User user;
}
