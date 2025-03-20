package com.shakkib.netflixclone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class AdminAccount {

    @Id
    private Long id;

    private String password;

    @OneToOne
    @JoinColumn(name = "admin_seq")
    private Admin admin;
}