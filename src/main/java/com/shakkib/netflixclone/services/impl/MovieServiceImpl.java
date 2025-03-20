package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.dtoes.MovieDTO;
import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.entity.Genre;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.repository.GenreRepository;
import com.shakkib.netflixclone.repository.MovieRepository;
import com.shakkib.netflixclone.services.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MovieServiceImpl implements MovieService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);
//
//    private final MovieRepository movieRepository;
//    private final UserServiceImpl userServiceImpl;
//
//    @Override
//    public List<Movie> fetchMovie(Long userId) throws MovieDetailsNotFoundException {
//        LOGGER.debug("Finding movies for user: " + userId);
//        LOGGER.info("fetchMovie method is triggered for user: " + userId);
//
//        List<Movie> movies = movieRepository.findAllByUserId(userId);
//        if (movies.isEmpty()) {
//            throw new MovieDetailsNotFoundException("No movies found for user ID: " + userId);
//        }
//
//        LOGGER.info("Returning {} movies for user {}", movies.size(), userId);
//        return movies;
//    }
//
//    @Override
//    public Movie addMovie(Movie movie) {
//        LOGGER.debug("Adding movie: " + movie);
//        LOGGER.info("addMovie method triggered");
//        return movieRepository.save(movie);
//    }
//
//    @Override
//    public boolean deleteMovie(Long movieId) {
//        LOGGER.debug("Deleting movie with ID: " + movieId);
//        if (!movieRepository.existsById(movieId)) {
//            LOGGER.warn("Movie with ID {} not found", movieId);
//            return false;
//        }
//        movieRepository.deleteById(movieId);
//        return true;
//    }
//
//    public boolean checkUser(Long userId) throws UserDetailsNotFoundException {
//        return userServiceImpl.checkUserByUserId(userId);
//    }
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }


    @Override
    public List<MovieListDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(MovieListDTO::new).collect(Collectors.toList());
    }


    @Override
    public Optional<MovieDTO> getMovieById(Long movieId) {
        return movieRepository.findById(movieId).map(MovieDTO::new);
    }


    @Override
    public List<MovieListDTO> getMoviesByGenre(String genreName) {
        Optional<Genre> genre = genreRepository.findByGenreIgnoreCase(genreName);
        if (genre.isEmpty())
            return List.of();
        List<Movie> movies = movieRepository.findByGenre(genre.get());
        return movies.stream().map(MovieListDTO::new).collect(Collectors.toList());
    }


    @Override
    public Optional<MovieDTO> getMovieByTitle(String title) {
        return movieRepository.findFirstByTitleIgnoreCase(title).map(MovieDTO::new);
    }


    @Override
    public List<MovieListDTO> searchMoviesByKeyword(String keyword) {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(keyword);
        return movies.stream().map(MovieListDTO::new).collect(Collectors.toList());
    }

    //영화 저장 메서드 추가
    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
