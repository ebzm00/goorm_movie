package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.repository.MovieRepository;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.exceptions.MovieDetailsNotFoundException;
import com.shakkib.netflixclone.exceptions.UserDetailsNotFoundException;
import com.shakkib.netflixclone.services.MovieService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public List<Movie> fetchMovie(Long userId) throws MovieDetailsNotFoundException {
        LOGGER.debug("Finding movies for user: " + userId);
        LOGGER.info("fetchMovie method is triggered for user: " + userId);

        List<Movie> movies = movieRepository.findAllByUserId(userId);
        if (movies.isEmpty()) {
            throw new MovieDetailsNotFoundException("No movies found for user ID: " + userId);
        }

        LOGGER.info("Returning {} movies for user {}", movies.size(), userId);
        return movies;
    }

    @Override
    public Movie addMovie(Movie movie) {
        LOGGER.debug("Adding movie: " + movie);
        LOGGER.info("addMovie method triggered");
        return movieRepository.save(movie);
    }

    @Override
    public boolean deleteMovie(Long movieId) {
        LOGGER.debug("Deleting movie with ID: " + movieId);
        if (!movieRepository.existsById(movieId)) {
            LOGGER.warn("Movie with ID {} not found", movieId);
            return false;
        }
        movieRepository.deleteById(movieId);
        return true;
    }

    public boolean checkUser(Long userId) throws UserDetailsNotFoundException {
        return userServiceImpl.checkUserByUserId(userId);
    }
}
