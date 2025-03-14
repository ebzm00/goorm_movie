package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserAccounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
