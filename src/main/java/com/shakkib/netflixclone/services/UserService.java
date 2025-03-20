package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.dtoes.UserDTO;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO); // 구현 없이 메서드 시그니처만 선언
    UserDTO findUser(Long id) throws UserDetailsNotFoundException;

    List<UserDTO> getAllUsers(); // 모든 사용자 조회

    // 사용자 상태 변경 메서드 250319 GSHAM
    void changeUserStatus (Long userId, boolean deleteFlag) throws UserDetailsNotFoundException;
}
