package com.shakkib.netflixclone.entity;

import com.shakkib.netflixclone.dtoes.JoinDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Getter
    private String password;

    @Column(nullable = false)
    @Getter
    private String nickname;

//    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Getter
    private boolean deleteFlag; // int -> boolean 형변환

    @Getter
    private Role role;

    private String CardNumber;
    private String phoneNumber;
    private String profileImageUrl;
    private String Remark;

    @Column(nullable = false, updatable = false)
    @Getter
    private LocalDateTime createdAt = LocalDateTime.now(); //자동생성
    private LocalDateTime updatedAt;


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

    public User(JoinDTO.Request request, PasswordEncoder passwordEncoder) {
        this.email = request.getEmail();
        this.password = passwordEncoder.encode(request.getPassword());
        this.nickname = request.getNickname();
        this.createdAt = LocalDateTime.now();
        this.address = "test";
        this.role = Role.USER;
        //다른건 나중에 추가
    }

    public User(JoinDTO.Request request, PasswordEncoder passwordEncoder,Role role) {
        this.email = request.getEmail();
        this.password = passwordEncoder.encode(request.getPassword());
        this.nickname = request.getNickname();
        this.createdAt = LocalDateTime.now();
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
        this.updatedAt = LocalDateTime.now();
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}