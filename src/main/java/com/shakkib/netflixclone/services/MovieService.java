package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.exceptions.MovieDetailsNotFoundException;
import java.util.List;

public interface MovieService {
    List<Movie> fetchMovie(Long userId) throws MovieDetailsNotFoundException;
    Movie addMovie(Movie movie);
    boolean deleteMovie(Long movieId);
}
