package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.jwt.JwtUtil;
import com.shakkib.netflixclone.services.ReissueService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReissueController {


    private final ReissueService reissueService;
    private final JwtUtil jwtUtil;

    public ReissueController(ReissueService reissueService, JwtUtil jwtUtil) {
        this.reissueService = reissueService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("reissue") //refresh 토큰을 이용한 재발급
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        return reissueService.reissue(request, response);
    }
}
