package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int deleteFlag;

    private String CardNumber;
    private String phoneNumber;
    private String profileImageUrl;
    private String Remark;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @OneToOne
    private UserAccounts userAccounts;

    public String getEmail() {
        return email;
    }
}