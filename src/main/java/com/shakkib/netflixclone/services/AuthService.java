package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.dtoes.JoinDTO;
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

    public boolean Join(JoinDTO.Request request){

        boolean userExists = userRepository.existsByEmail(request.getEmail()).orElseThrow();

        if(!userExists){
            User user = new User(request, passwordEncoder);
            userRepository.save(user);
            return true;
        }

        return false;

    }
}
