package com.shakkib.netflixclone.dtoes;

import com.shakkib.netflixclone.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JoinDTO {

    @Getter
    @Setter
    public static class Request{
        private String email;
        private String password;
        private String nickname;
        //다른건 나중에 추가


    }
}