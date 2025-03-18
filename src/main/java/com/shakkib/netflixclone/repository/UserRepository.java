package com.shakkib.netflixclone.repository;

import com.shakkib.netflixclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserById(String id);

    Optional<Boolean> existsByEmail(String email);

    Optional<Boolean> existsByPassWord(String password);

    Optional<User> findUserByEmail(String email);

    boolean existsById(String user_id);

    Optional<Boolean> existsByEmailAndPassWord(String email, String passWord);

    @Query("{'email':?0,'password':?1}")
    Optional<User> findUserByEmailAndPassWord(String email, String passWord);

    Optional<User> findBySeq (Long id);
}