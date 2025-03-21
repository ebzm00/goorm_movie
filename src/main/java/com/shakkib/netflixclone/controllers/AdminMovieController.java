package com.shakkib.netflixclone.controllers;


import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {

    private final AdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<List<MovieListDTO>> getAllMoviesForAdmin() {
        List<MovieListDTO> movies = adminService.getAllMoviesIncludingInactive();
        return ResponseEntity.ok(movies);
    }

    @PutMapping("/{movieId}/deactivate")
    public ResponseEntity<String> deactivateMovie(@PathVariable Long movieId) {
        try {
            adminService.deactivateMovie(movieId);
            return ResponseEntity.ok("영화가 비활성화되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 영화를 찾을 수 없습니다.");
        }
    }
}
