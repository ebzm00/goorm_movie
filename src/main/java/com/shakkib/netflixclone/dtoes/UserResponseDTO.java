package com.shakkib.netflixclone.dtoes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;                    //순번
    private String email;              //이메일
    private String nickname;           // 닉네임
    private LocalDateTime createDate; // 가입일
    private String status;            //상태
    private boolean deleteFlag;        //중지처리
}
