package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.dtoes.CustomUserDetails;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow();

        if (user != null) {
            return new CustomUserDetails(user);
        }

        return null;
    }
}
