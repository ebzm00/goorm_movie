package com.shakkib.netflixclone.entity;

import com.shakkib.netflixclone.dtoes.JoinDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    @Getter
    private String email;

    @Getter
    private String password;

    @Column(nullable = false)
    @Getter
    private String nickname;

//    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean deleteFlag; // int -> boolean 형변환

    @Getter
    private Role role;

    private String CardNumber;
    private String phoneNumber;
    private String profileImageUrl;
    private String Remark;

    @Column(nullable = false, updatable = false)
    @Getter
    private LocalDateTime createDate = LocalDateTime.now(); //자동생성
    private LocalDateTime updateDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_accounts_id") // 외래키 매핑 명확화
    private UserAccounts userAccounts;

    //추가: 생성자 정의
    public User(String nickname,String email, LocalDateTime createDate, String password) {
        this.nickname = nickname;
        this.email = email;
        this.createDate = LocalDateTime.now();
        this.password = password;
    }

    public User(String nickname,String email, LocalDateTime createDate) {
        this.nickname = nickname;
        this.email = email;
        this.createDate = LocalDateTime.now();
    }

    public User(JoinDTO.Request request, PasswordEncoder passwordEncoder) {
        this.email = request.getEmail();
        this.password = passwordEncoder.encode(request.getPassword());
        this.nickname = request.getNickname();
        this.createDate = LocalDateTime.now();
        this.address = "test";
        this.role = Role.USER;
        //다른건 나중에 추가
    }

    public User(JoinDTO.Request request, PasswordEncoder passwordEncoder,Role role) {
        this.email = request.getEmail();
        this.password = passwordEncoder.encode(request.getPassword());
        this.nickname = request.getNickname();
        this.createDate = LocalDateTime.now();
        this.address = "test";
        this.role = Role.ADMIN;
        //다른건 나중에 추가
    }

    public User(String email, Role role) {
        this.email = email;
        this.role = role;
        this.password = "temppassword";
    }

    @PreUpdate
    public void setUpdateDate() {
        this.updateDate = LocalDateTime.now();
    }

}