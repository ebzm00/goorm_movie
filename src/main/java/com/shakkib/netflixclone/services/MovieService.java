package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.dtoes.MovieDTO;
import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.exceptions.MovieDetailsNotFoundException;
import java.util.List;
import java.util.Optional;

public interface MovieService {
//    List<Movie> fetchMovie(Long userId) throws MovieDetailsNotFoundException;
//    Movie addMovie(Movie movie);
//    boolean deleteMovie(Long movieId);

    List<MovieListDTO> getAllMovies();  //
    List<MovieListDTO> getMoviesByGenre(String genre);
    Optional<MovieDTO> getMovieByTitle(String title);
    Optional<MovieDTO> getMovieById(Long movieId);
    List<MovieListDTO> searchMoviesByKeyword(String keyword);
}