package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.UserDTO;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
import com.shakkib.netflixclone.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.qos.logback.classic.Logger;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/user/v1")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserController.class);

    //https://api.themoviedb.org/3/movie/550?api_key=b4eda142837c245432c018af5c4ec342

@PostMapping("/create")
public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
    LOGGER.info("Pass UserDTO details: " + userDTO);

    // 그냥 userDTO를 service로 넘기면 됨
    UserDTO response = userServiceImpl.createUser(userDTO);

    LOGGER.info("User created: " + response);

    // 반환값 그대로 사용
    return ResponseEntity.ok(response);
}

//    @PostMapping("/create")
//    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
//        User user = convertUserDTOToUserEntity(userDTO);
//        LOGGER.info("Pass UserDTO detials"+ userDTO);
//        User response = userServiceImpl.createUser(user);
//        LOGGER.info("User created: " + response);
//        UserDTO responseDTO = convertUserEntityToUserDTO(response);
//        return ResponseEntity.ok(responseDTO);
//    }

    @GetMapping("/find/{id}") //id == seq
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) throws UserDetailsNotFoundException {
        // userService에서 이미 UserDTO를 반환하므로 바로 받아서 사용
        UserDTO response = userServiceImpl.findUser(id);
        // UserDTO를 그대로 반환
        return ResponseEntity.ok(response);
    }

    //    @GetMapping("/find/{id}")
//    public ResponseEntity<UserDTO> getUser(@PathVariable("id") String id) throws UserDetailsNotFoundException {
//        UserDTO userDTO = convertUserEntityToUserDTO(response);
//        return ResponseEntity.ok(userDTO);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserDTO userDTO, HttpSession httpSession) throws UserDetailsNotFoundException{
//        //User user1 = convertUserDTOToUserEntity(userDTO);
//        //User myuser = null;
//            String response = userServiceImpl.checkLogin(userDTO.getEmail(), userDTO.getPassword(), httpSession);
//            return response != null ? ResponseEntity.ok(response): ResponseEntity.badRequest().body(null);
//    }

    @GetMapping("/logout")
    String logout(HttpSession httpSession) {
        System.out.println("User log out");
        httpSession.invalidate();
        return "log out";
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserDTO userDTO, HttpSession httpSession) throws UserDetailsNotFoundException {
        LOGGER.info("Joining request received: " + userDTO);

        //UserDTO를 User엔티티로 변환
        User user = convertUserDTOToUserEntity(userDTO);

        //유저가 이미 존재하는지 확인
        boolean userExists = userServiceImpl.joiningMethod(user);

        if (userExists) {
            return new ResponseEntity<>("User already exists", HttpStatus.ALREADY_REPORTED);
        } else {
            // UserDTO로 변환한 후 createUser 메서드 호출
            UserDTO newUserDTO = userServiceImpl.createUser(userDTO); //userDTO로 넘김
            httpSession.setAttribute("USERID_SESSION", newUserDTO.getEmail()); // userDTO에서 email가져오기
            return ResponseEntity.ok(newUserDTO.getEmail());
        }
    }

//    @PostMapping("/join")
//    public ResponseEntity<String> join(@RequestBody UserDTO userDTO, HttpSession httpSession) throws UserDetailsNotFoundException {
//        LOGGER.info("Joining request received: " + userDTO);
//        User user = convertUserDTOToUserEntity(userDTO);
//        boolean userExists = userServiceImpl.joiningMethod(user);
//
//        if (userExists) {
//            return new ResponseEntity<>("User already exists", HttpStatus.ALREADY_REPORTED);
//        } else {
//            User newUser = userServiceImpl.createUser(user);
//            httpSession.setAttribute("USERID_SESSION", newUser.getEmail());
//            return ResponseEntity.ok(newUser.getEmail());
//        }
//    }



//i havent used model mapper instead i have written my own code
    private UserDTO convertUserEntityToUserDTO(User user){
        UserDTO userDTO = new UserDTO(user.getEmail(),user.getNickname(),user.getEmail(),user.getCreatedAt());
        return userDTO;
    }

    private User convertUserDTOToUserEntity(UserDTO userDTO) {
        User user = new User(userDTO.getEmail(),userDTO.getNickname(),userDTO.getCreateDate());
        return user;
    }


//    private User convertUserDTOToUserEntity(UserDTO userDTO){
//        User user = new User(userDTO.getId(),userDTO.getName(), userDTO.getEmail());
//        return user;
//    }
}
