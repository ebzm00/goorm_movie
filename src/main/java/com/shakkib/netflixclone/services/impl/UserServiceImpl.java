package com.shakkib.netflixclone.services.impl;


import com.shakkib.netflixclone.repository.UserAccountsRepository;
import com.shakkib.netflixclone.repository.UserRepository;
import com.shakkib.netflixclone.dtoes.UserDTO;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.entity.UserAccounts;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
import com.shakkib.netflixclone.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
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

        return new UserDTO(newUser.getEmail(), newUser.getNickname(), newUser.getCreateDate());
    }

    @Override //id == seq
    public UserDTO findUser(Long id) throws UserDetailsNotFoundException {
        // User 객체를 가져옴
        User user = userRepository.findById(id).orElseThrow(() -> new UserDetailsNotFoundException("User does not exists"));

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
