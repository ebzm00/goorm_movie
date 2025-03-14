package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;

@Entity
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

}
