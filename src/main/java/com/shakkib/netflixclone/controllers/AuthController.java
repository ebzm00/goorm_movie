package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.JoinDTO;
import com.shakkib.netflixclone.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/join")
    public ResponseEntity<Object> join(@RequestBody JoinDTO.Request request) {

        if (authService.Join(request)) return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();

        }

    }


}
