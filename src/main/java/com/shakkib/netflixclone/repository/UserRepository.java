package com.shakkib.netflixclone.repository;

import com.shakkib.netflixclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;

public interface UserRepository extends JpaRepository<User, Long> {
    ScopedValue<Object> findAllBySeq(Long seq);
}
