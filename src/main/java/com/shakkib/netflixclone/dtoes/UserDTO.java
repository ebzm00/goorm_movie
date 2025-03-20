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
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private LocalDateTime createDate;
    private boolean deleteFlag;

    public UserDTO(String email, String nickname,LocalDateTime registeredDate) {
        this.email = email;
        this.nickname = nickname;
        this.createDate = registeredDate;
    }

    public UserDTO(String nickname, String email, String password, LocalDateTime registeredDate) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.createDate = registeredDate;
    }

    public UserDTO(String nickname,String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public UserDTO(Long id, String email,String nickname,LocalDateTime createDate, boolean deleteFlag) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.createDate = createDate;
        this.deleteFlag = deleteFlag;
    }



}
