package com.shakkib.netflixclone.dtoes;

import com.shakkib.netflixclone.entity.Movie;
import lombok.Getter;
import lombok.Setter;

/*
CHW
영화 조회 DTO
 */
@Getter
@Setter
public class MovieListDTO {
    private Long movieId;
    private String title;
    private String posterPath;

    public MovieListDTO(Movie movie) {
        this.movieId = movie.getMovieId();
        this.title = movie.getTitle();
        this.posterPath = movie.getPosterPath();
    }

    public MovieListDTO(Long movieId, String title, String posterPath) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
    }

}