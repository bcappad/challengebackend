package com.alkemy.disney.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;
    @Column(name = "email", unique = true)
    private String email;
    private String password;



}
