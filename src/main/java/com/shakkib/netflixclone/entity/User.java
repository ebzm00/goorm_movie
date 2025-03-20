package com.shakkib.netflixclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false,unique = true)
    @Getter
    private String email;

    private String password;

    @Column(nullable = false)
    @Getter
    private String nickname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Getter
    private boolean deleteFlag; // int -> boolean 형변환

    private String CardNumber;
    private String phoneNumber;
    private String profileImageUrl;
    private String Remark;

    @Column(nullable = false, updatable = false)
    @Getter
    private LocalDateTime createdAt = LocalDateTime.now(); //자동생성
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserAccounts userAccounts;

    //추가: 생성자 정의
    public User(String nickname,String email, LocalDateTime createdAt, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    public User(String nickname,String email, LocalDateTime createdAt) {
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}