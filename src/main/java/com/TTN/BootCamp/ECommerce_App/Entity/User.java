package com.TTN.BootCamp.ECommerce_App.Entity;

import com.TTN.BootCamp.ECommerce_App.DTO.Auditing.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name="user_gen", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Column(name = "Email")
    private String email;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Middle_Name")
    private String middleName;

    @Column(name = "Last_Name")
    private String lastName;

    @Column(name = "Password")
    private String password;

    @Column(name = "Is_Deleted")
    private boolean isDeleted;

    @Column(name = "Is_Active")
    private boolean isActive;

    @Column(name = "Is_Expired")
    private boolean isExpired;

    @Column(name = "Is_Locked")
    private boolean isLocked;

    @Column(name = "Invalid_Attempt_Count")
    private int invalidAttemptCount;

    @Column(name = "Password_Update_Date")
    private Date passwordUpdateDate;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Customer customer;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Seller seller;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "User_Role"
            ,joinColumns = @JoinColumn(name = "User_id", referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "Role_id", referencedColumnName = "id"))
    private Role role;

}
