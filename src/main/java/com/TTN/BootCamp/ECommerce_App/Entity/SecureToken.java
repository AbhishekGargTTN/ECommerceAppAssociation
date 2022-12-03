package com.TTN.BootCamp.ECommerce_App.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SecureToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secureToken_gen")
    @SequenceGenerator(name="secureToken_gen", sequenceName = "secureToken_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Column(name = "Secure_Token")
    private String secureToken;

    private LocalDateTime createdDate;

    @OneToOne()
    @JoinColumn(name = "User_Id")
//    @JsonBackReference
    private User user;

    public SecureToken(User user) {
        secureToken = UUID.randomUUID().toString();
        createdDate = LocalDateTime.now();
        this.user = user;
    }
}
