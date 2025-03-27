package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.dtoes.JoinDTO;
import com.shakkib.netflixclone.entity.Role;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
    public boolean join(JoinDTO.Request request){

        boolean userExists = userRepository.existsByEmail(request.getEmail());

        if(!userExists){
            User user = new User(request, passwordEncoder);
            userRepository.save(user);
            return true;
        }

        return false;

    }

    //관리자 회원가입
    public boolean adminJoin(JoinDTO.Request request){

        boolean userExists = userRepository.existsByEmail(request.getEmail());

        if(!userExists){
            User user = new User(request, passwordEncoder, Role.ADMIN);
            userRepository.save(user);
            return true;
        }

        return false;

    }
}
