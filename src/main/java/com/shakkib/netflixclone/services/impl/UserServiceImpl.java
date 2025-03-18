package com.shakkib.netflixclone.services.impl;


import javax.servlet.http.HttpSession;

import com.shakkib.netflixclone.Repository.UserAccountsRepository;
import com.shakkib.netflixclone.Repository.UserRepository;
import com.shakkib.netflixclone.dtoes.UserDTO;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.entity.UserAccounts;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
import com.shakkib.netflixclone.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserAccountsRepository userAccountsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        //User 생성 후 저장
        User newUser = new User(userDTO.getNickname(),userDTO.getEmail(),userDTO.getCreateDate());
        User savedUser = userRepository.save(newUser);

        // 비밀번호 해싱 후 UserAccounts 저장
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        UserAccounts userAccount = new UserAccounts(savedUser, hashedPassword);
        userAccountsRepository.save(userAccount);

        return new UserDTO(savedUser.getEmail(), savedUser.getNickname(), savedUser.getCreateDate());
    }

    @Override
    public UserDTO findUser(String id) throws UserDetailsNotFoundException {
        // User 객체를 가져옴
        User user = userRepository.findUserById(id).orElseThrow(() -> new UserDetailsNotFoundException("User does not exists"));

        // User 객체를 UserDTO로 변환
        UserDTO userDTO = new UserDTO(user.getEmail(), user.getNickname(), user.getCreateDate());

        return userDTO;
    }

    //    @Override
//    public UserDTO findUser(String id) throws UserDetailsNotFoundException {
//        User newUser = userRepository.findUserById(id).orElseThrow(()-> new UserDetailsNotFoundException("User does not exists"));
//        return newUser;
//    }

    public Boolean checkUserByEmail(String email) throws UserDetailsNotFoundException {
        return userRepository.existsByEmail(email).orElseThrow(() -> new UserDetailsNotFoundException("User does not exists"));
    }
    public User findUserByEmail(String email) throws UserDetailsNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(()->new UserDetailsNotFoundException("User does not exists"));
    }
    public boolean checkUserByUserId(String user_id) throws UserDetailsNotFoundException{
        boolean flag = userRepository.existsById(user_id);
        if(flag) {
            return flag;
        }
        else throw new UserDetailsNotFoundException("User does not exists with passed details");
    }

    public boolean checkUserExistsByEmailAndPassword(String email, String passWord) throws UserDetailsNotFoundException{
        return userRepository.existsByEmailAndPassWord(email,passWord).orElseThrow(()-> new UserDetailsNotFoundException("Wrong email and password"));
    }

    public boolean checkUserByPassWord(String password) throws UserDetailsNotFoundException{
        boolean flag = userRepository.existsByPassWord(password).orElseThrow(()-> new UserDetailsNotFoundException("user does not exists with password"));
        return flag;
    }

    public User findUserByEmailAndPassWord(String email,String password) throws UserDetailsNotFoundException{
        User user = userRepository.findUserByEmailAndPassWord(email, password).orElseThrow(()
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
