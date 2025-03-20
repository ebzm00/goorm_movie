package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "admin")
    private AdminAccount adminAccount;
}
