package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.MovieDTO;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.exceptions.MovieDetailsNotFoundException;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
import com.shakkib.netflixclone.services.impl.MovieServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/user/v1/mylist")
public class MovieController {

    private final MovieServiceImpl movieServiceImpl;

    @PostMapping("/add")
    public String addMovieInMyList(@RequestBody MovieDTO movieDTO) {
        Movie movie = convertMovieDTOToMovieEntity(movieDTO);
        Movie response = movieServiceImpl.addMovie(movie);
        String s = response.getSeq() != null ? "success" : "fail";
        System.out.println(s);
        return s;
    }

    @GetMapping("/allmovies/{user_id}")
    public ResponseEntity<List<MovieDTO>> fetchAllMoviesOfUser(@PathVariable String user_id)
            throws UserDetailsNotFoundException, MovieDetailsNotFoundException {
        movieServiceImpl.checkUser(user_id);
        List<Movie> movies = movieServiceImpl.fetchMovie(user_id);
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            movieDTOS.add(convertMovieEntityToMovieDTO(movie));
        }
        return new ResponseEntity<>(movieDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{movie_id}")
    public String deleteMovieFromUserList(@PathVariable Long movie_id) {
        boolean flag = movieServiceImpl.deleteMovie(movie_id);
        return flag ? "success" : "fail";
    }

    private MovieDTO convertMovieEntityToMovieDTO(Movie movie) {
        return new MovieDTO(
                movie.getSeq(),
                movie.getMovieId(),
                movie.getTitle(),
                movie.getOriginalTitle(),
                movie.getPosterPath(),
                movie.isAdult(),
                movie.getOverview(),
                movie.getReleaseDate(),
                movie.getCreatedAt(),
                movie.getUpdatedAt()
        );
    }


    private Movie convertMovieDTOToMovieEntity(MovieDTO movieDTO) {
        return new Movie(
                movieDTO.getSeq(),
                movieDTO.getMovieId(),
                movieDTO.getTitle(),
                movieDTO.getOriginalTitle(),
                movieDTO.getPosterPath(),
                movieDTO.isAdult(),
                movieDTO.getOverview(),
                movieDTO.getReleaseDate(),
                movieDTO.getCreatedAt(),
                movieDTO.getUpdatedAt()
        );
    }

}