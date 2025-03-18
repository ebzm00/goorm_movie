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
    private Long seq;

    @Column(nullable = false,unique = true)
    @Getter
    private String email;

    @Column(nullable = false)
    @Getter
    private String nickname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean deleteFlag; // int -> boolean 형변환

    private String CardNumber;
    private String phoneNumber;
    private String profileImageUrl;
    private String Remark;

    @Column(nullable = false, updatable = false)
    @Getter
    private LocalDateTime createDate = LocalDateTime.now(); //자동생성
    private LocalDateTime updateDate;

    //추가: 생성자 정의
    public User(String nickname,String email, LocalDateTime createDate) {
        this.nickname = nickname;
        this.email = email;
        this.createDate = LocalDateTime.now();
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_accounts_id") // 외래키 매핑 명확화
    private UserAccounts userAccounts;


    @PreUpdate
    public void setUpdateDate() {
        this.updateDate = LocalDateTime.now();
    }

}