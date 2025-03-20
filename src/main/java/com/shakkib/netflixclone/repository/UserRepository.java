package com.shakkib.netflixclone.repository;

import com.shakkib.netflixclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<Boolean> existsByEmail(String email);

    Optional<Boolean> existsByPassword(String password);

    Optional<User> findUserByEmail(String email);

    Optional<Boolean> existsByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    boolean existsById(Long id);

}