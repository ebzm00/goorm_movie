package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.CustomUserDetails;
import com.shakkib.netflixclone.dtoes.JoinDTO;
import com.shakkib.netflixclone.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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