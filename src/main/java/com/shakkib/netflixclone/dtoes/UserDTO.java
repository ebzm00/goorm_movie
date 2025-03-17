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

    public UserDTO(String name, String email, LocalDateTime registeredDate) {
        this.name = name;
        this.email = email;
        this.createDate = LocalDateTime.now();
    }

    public UserDTO(String name, String email, String password, LocalDateTime registeredDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createDate = LocalDateTime.now();
    }
}
