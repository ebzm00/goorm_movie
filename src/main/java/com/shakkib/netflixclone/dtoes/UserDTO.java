package com.shakkib.netflixclone.dtoes;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class UserDTO {
    private String id;
    private String nickname;
    private String email;
    private String password;
    private LocalDateTime createDate;

    public UserDTO(String nickname, String email, LocalDateTime registeredDate) {
        this.nickname = nickname;
        this.email = email;
        this.createDate = LocalDateTime.now();
    }

    public UserDTO(String nickname, String email, String password, LocalDateTime registeredDate) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.createDate = LocalDateTime.now();
    }

    public UserDTO(String nickname,String email) {
        this.nickname = nickname;
        this.email = email;
    }

}
