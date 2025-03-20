package com.shakkib.netflixclone.services.impl;


import com.shakkib.netflixclone.repository.UserAccountsRepository;
import com.shakkib.netflixclone.repository.UserRepository;
import com.shakkib.netflixclone.dtoes.UserDTO;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.entity.UserAccounts;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
import com.shakkib.netflixclone.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserAccountsRepository userAccountsRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        // 비밀번호 해싱 후 User 저장
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        User newUser = new User(userDTO.getNickname(),userDTO.getEmail(),userDTO.getCreateDate(),userDTO.getPassword());

        userRepository.save(newUser);

        return new UserDTO(newUser.getEmail(), newUser.getNickname(), newUser.getCreatedAt());
    }

    @Override //id == seq
    public UserDTO findUser(Long id) throws UserDetailsNotFoundException {
        // User 객체를 가져옴
        User user = userRepository.findById(id).orElseThrow(() -> new UserDetailsNotFoundException("User does not exists"));

        // User 객체를 UserDTO로 변환
        UserDTO userDTO = new UserDTO(user.getEmail(), user.getNickname(), user.getCreatedAt());

        return userDTO;
    }

    // 250319 GSHAM 관리자 시점 사용자 계정 조회 기능 구현
    @Override
    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(user -> new UserDTO(
                            user.getId(),          // ID
                            user.getEmail(),       // 이메일
                            user.getNickname(),    // 닉네임
                            user.getCreatedAt(),  // 생성 날짜
                            user.isDeleteFlag()    // 삭제 여부
                    ))
                    .collect(Collectors.toList()); // List<UserDTO>로 변환
        } catch (Exception e) {
            LOGGER.error("Error retrieving users: ", e);
            throw new RuntimeException("Failed to retrieve users");
        }
    }

    // 250319 GSHAM 사용자 상태 변경 메서드
    public void changeUserStatus(Long userId, boolean deleteFlag) throws UserDetailsNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setDeleteFlag(deleteFlag); // 상태 변경
            userRepository.save(user); //상태 변경된 사용자 저장
        } else {
            throw new UserDetailsNotFoundException("User not found");
        }
    }

    public Boolean checkUserByEmail(String email) throws UserDetailsNotFoundException {
        return userRepository.existsByEmail(email);
    }
    public User findUserByEmail(String email) throws UserDetailsNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(()->new UserDetailsNotFoundException("User does not exists"));
    }
    public boolean checkUserByUserId(Long user_id) throws UserDetailsNotFoundException{
        boolean flag = userRepository.existsById(user_id);
        if(flag) {
            return flag;
        }
        else throw new UserDetailsNotFoundException("User does not exists with passed details");
    }

    public boolean checkUserExistsByEmailAndPassword(String email, String passWord) throws UserDetailsNotFoundException{
        return userRepository.existsByEmailAndPassword(email,passWord).orElseThrow(()-> new UserDetailsNotFoundException("Wrong email and password"));
    }

    public boolean checkUserByPassWord(String password) throws UserDetailsNotFoundException{
        boolean flag = userRepository.existsByPassword(password).orElseThrow(()-> new UserDetailsNotFoundException("user does not exists with password"));
        return flag;
    }

    public User findUserByEmailAndPassWord(String email,String password) throws UserDetailsNotFoundException{
        User user = userRepository.findUserByEmailAndPassword(email, password).orElseThrow(()
        -> {
                LOGGER.error("User details not found for the email: " + email);
                return new UserDetailsNotFoundException("User details not found for the email : " + email);
            });
        return user;
    }

//    public String checkLogin(String email, String passWord, HttpSession httpSession) throws UserDetailsNotFoundException{
//        User user = findUserByEmail(email);
//        LOGGER.info("the user in service layer details :"+user);
//        LOGGER.info(email+" + "+passWord+" + "+user.getPassWord());
//        boolean flag=true;
//        //System.out.println("type"+user.getPassword().getClass().getName());
//        if(user.getPassWord().compareTo(passWord)==0){
//            flag =true;
//            //System.out.println("if executed");
//        }else{
//            //System.out.println("else executed");
//            flag=false;
//        }
//        if(flag){
//            LOGGER.info("inside the true statement : ");
//            httpSession.setAttribute("USERID_SESSION", user.getEmail());
//            return user.getId();
//        }
//        else throw new UserDetailsNotFoundException("UserName and Password are wrong");
//    }

    public boolean joiningMethod(User user) throws UserDetailsNotFoundException{
        LOGGER.info("User details in joning method :"+user);
        boolean flag = checkUserByEmail(user.getEmail());
        if(flag)
        return true;
        else return false;    
    }

    /* GS 작업한게 아님 */
   // @Override
   // public List<String> moviesOfUser(String userId) {
     //   System.out.printf("Finding movies of userList %s%n",userId);
     //   List<String> list = userDao.findAllMoviesById(userId);
     //   System.out.printf("Returning the saved movies of users %s%n",list.size());
     //   return list;
    //}
}
