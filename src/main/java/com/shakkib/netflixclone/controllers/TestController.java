package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roletest/{id}")
    public ResponseEntity<?> roleteJoin(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id) {
        String email = userDetails.getEmail();
        System.out.println(id+"// "+email);

        return ResponseEntity.ok().build();
    }

}
