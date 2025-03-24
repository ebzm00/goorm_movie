package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.dtoes.MovieCreateDTO;
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
//
    List<MovieListDTO> getAllMovies();  //
    List<MovieListDTO> getMoviesByGenre(String genre);
    Optional<MovieDTO> getMovieByTitle(String title);
    Optional<MovieDTO> getMovieById(Long movieId);
    List<MovieListDTO> searchMoviesByKeyword(String keyword);

    //250320 GSHAM 영화 저장 메서드 추가 -> 저장 하기위한 DTO -> Entity 변환
    Movie saveMovie(Movie movie); //영화 저장 메서드 추가
    Movie convertMovieDTOToMovieEntity(MovieCreateDTO movieCreateDTO);
}