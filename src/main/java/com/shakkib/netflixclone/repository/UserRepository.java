package com.shakkib.netflixclone.repository;

import com.shakkib.netflixclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<Boolean> existsByPassword(String password);

    Optional<User> findUserByEmail(String email);

    Optional<Boolean> existsByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    boolean existsById(Long id);

    // 250321 GSHAM 삭제 여부에 따른 사용자 조회 메서드 추가
    List<User> findByDeleteFlag(boolean deleteFlag);

}