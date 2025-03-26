package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
20250321 CHW
관리자 영화 관리 컨트롤러
 */
@RestController
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {

    private final AdminService adminService;

    /*
    관리자 영화 목록 조회(모든 영화 조회)
    return 모든 영화
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<MovieListDTO>> getAllMoviesForAdmin() {
        List<MovieListDTO> movies = adminService.getAllMoviesIncludingInactive();
        return ResponseEntity.ok(movies);
    }

    /*
     20250321 CHW
     관리자 영화 비활성화 처리 기능
     - 영화 없을 시 예외 처리
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{movieId}/deactivate")
    public ResponseEntity<String> deactivateMovie(@PathVariable("movieId") Long movieId) {
        try {
            adminService.deactivateMovie(movieId);
            return ResponseEntity.ok("영화가 비활성화되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 영화를 찾을 수 없습니다.");
        }
    }

    /*
     20250321 CHW
     관리자 영화 활성화 처리 기능
     - 영화 없을 시 예외 처리
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{movieId}/activate")
    public ResponseEntity<String> activateMovie(@PathVariable("movieId") Long movieId) {
        try {
            adminService.activateMovie(movieId);
            return ResponseEntity.ok("영화가 다시 활성화되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 영화를 찾을 수 없습니다.");
        }
    }

}
