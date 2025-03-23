package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.MovieDTO;
import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.exceptions.MovieDetailsNotFoundException;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
import com.shakkib.netflixclone.services.MovieService;
import com.shakkib.netflixclone.services.impl.MovieServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 CHW
사용자 영화 관리 컨트롤러
 */
@RestController
@RequestMapping("/movies/")
public class MovieController {


    private final MovieService movieService;

    public MovieController(MovieService movieService) {

        this.movieService = movieService;
    }

    /*
     CHW
     사용자 영화 목록 조회
     (활성화 처리 된 것만 볼 수 있음)
    */
    @GetMapping
    public ResponseEntity<List<MovieListDTO>> getAllMovies() {
        //CHW getAllMovies가 활성화 여부 판단
        List<MovieListDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    /*
     CHW
     사용자 영화 디테일 조회
     (활성화 처리 된 것만 볼 수 있음)
    */
    @GetMapping("/detail/{movieId}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long movieId) {
        Optional<MovieDTO> movie = movieService.getMovieById(movieId);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*  CHW
        사용자 장르 검색 영화 목록 조회
        (활성화 처리 된 것만 볼 수 있음)
    */
    @GetMapping("/genre")
    public ResponseEntity<List<MovieListDTO>> getMoviesByGenre(@RequestParam String name) {
        List<MovieListDTO> movies = movieService.getMoviesByGenre(name);
        if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movies);
    }

    /*  CHW
        사용자 타이틀 검색 영화 목록 조회
        (활성화 처리 된 것만 볼 수 있음)
    */
    @GetMapping("/title")
    public ResponseEntity<MovieDTO> getMovieByTitle(@RequestParam String title) {
        Optional<MovieDTO> movie = movieService.getMovieByTitle(title);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*  CHW
        사용자  % 검색 영화 목록 조회
        (활성화 처리 된 것만 볼 수 있음)
    */

    @GetMapping("/search")
    public ResponseEntity<List<MovieListDTO>> searchMoviesByKeyword(@RequestParam String keyword) {
        List<MovieListDTO> movies = movieService.searchMoviesByKeyword(keyword);

        if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(movies);
    }

}