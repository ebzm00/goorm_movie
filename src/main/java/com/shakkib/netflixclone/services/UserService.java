package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.dtoes.UserDTO;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;

public interface UserService {
    UserDTO createUser(UserDTO userDTO); // 구현 없이 메서드 시그니처만 선언
    UserDTO findUser(Long id) throws UserDetailsNotFoundException;
}
