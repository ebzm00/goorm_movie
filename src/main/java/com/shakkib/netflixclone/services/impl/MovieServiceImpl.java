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

/*
CHW
영화 관리 서비스
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    /**
     * CHW
     * 활성화된(isUse = true) 모든 영화를 조회
     */
    @Override
    public List<MovieListDTO> getAllMovies() {
        /* 활성화 체크 부분 */
        return movieRepository.findAllByIsUseTrue()
                .stream()
                .map(MovieListDTO::new)
                .collect(Collectors.toList());
    }



    /**
     * CHW
     * 특정 ID 영화 조회
     */
    @Override
    public Optional<MovieDTO> getMovieById(Long movieId) {
        return movieRepository.findByIdAndIsUseTrue(movieId) //활성화 체크 부분
                .map(MovieDTO::new);
    }



    /**
     * CHW
     * 장르별 영화를 조회
     */
    @Override
    public List<MovieListDTO> getMoviesByGenre(String genreName) {
        Optional<Genre> genre = genreRepository.findByGenreIgnoreCase(genreName);
        if (genre.isEmpty())
            return List.of();

        List<Movie> movies = movieRepository.findByGenreAndIsUseTrue(genre.get());
        return movies.stream().map(MovieListDTO::new).collect(Collectors.toList());
    }




    /**
     * CHW
     * 타이틀별 영화 조회
     */
    @Override
    public Optional<MovieDTO> getMovieByTitle(String title) {
        return movieRepository.findFirstByTitleIgnoreCaseAndIsUseTrue(title)
                .map(MovieDTO::new);
    }


    /**
     * CHW
     * 키워드로 영화 조회
     */
    @Override
    public List<MovieListDTO> searchMoviesByKeyword(String keyword) {
        List<Movie> movies = movieRepository.searchMoviesWithKeyword(keyword);
        return movies.stream().map(MovieListDTO::new).collect(Collectors.toList());
    }


    //영화 저장 메서드 추가
    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
