package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.JoinDTO;
import com.shakkib.netflixclone.services.impl.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<Object> join(@RequestBody JoinDTO.Request request) {

        if (authService.join(request)) return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();

    }

    //관리자 회원가입 테스트
    @PostMapping("/adminjointest")
    public ResponseEntity<Object> adminJoin(@RequestBody JoinDTO.Request request) {

        if (authService.adminJoin(request)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();

    }

}