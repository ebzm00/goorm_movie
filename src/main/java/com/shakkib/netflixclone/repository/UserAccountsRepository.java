package com.shakkib.netflixclone.repository;

import com.shakkib.netflixclone.entity.UserAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountsRepository extends JpaRepository<UserAccounts, Long> {
    Optional<UserAccounts> findByUserId(Long userId);
}
