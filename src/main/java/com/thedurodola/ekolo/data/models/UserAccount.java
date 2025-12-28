package com.thedurodola.ekolo.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thedurodola.ekolo.data.models.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.annotation.EnumNaming;

import java.util.Date;

@Setter
@Getter
@Entity
@Table
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;
    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false,  unique = true)
    private String imageUrl;

    @Column(nullable = false,  unique = true)
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
