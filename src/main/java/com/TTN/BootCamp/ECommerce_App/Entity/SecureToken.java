package com.TTN.BootCamp.ECommerce_App.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SecureToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "Secure_Token")
    private String secureToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = Customer.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
//    @JsonBackReference
    private User user;

    public SecureToken(User user) {
        secureToken = UUID.randomUUID().toString();
        createdDate = new Date();
        this.user = user;
    }
}
