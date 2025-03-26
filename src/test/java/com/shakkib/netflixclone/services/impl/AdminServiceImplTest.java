package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.entity.Genre;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.repository.MovieRepository;
import com.shakkib.netflixclone.services.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    @DisplayName("관리자 영화 전체 조회 - 성공")
    void getAllMoviesIncludingInactive_shouldReturnMovies() {
        // given
        Genre genre = new Genre(1L, "액션");
        Movie movie = new Movie(
                1L, 100L, "타이틀", "오리지널", "poster.jpg", false,
                "설명", LocalDate.now(), genre,
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(movieRepository.findAll()).thenReturn(List.of(movie));

        // when
        List<MovieListDTO> result = adminService.getAllMoviesIncludingInactive();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("타이틀");
    }

    @Test
    @DisplayName("영화 비활성화 - 성공")
    void deactivateMovie_shouldSetIsUseFalse() {
        // given
        Movie movie = new Movie();
        movie.activate(); // 먼저 true 상태로

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        // when
        adminService.deactivateMovie(1L);

        // then
        assertThat(movie.isUse()).isFalse();
        verify(movieRepository).save(movie);
    }

    @Test
    @DisplayName("영화 비활성화 - 실패 (영화 없음)")
    void deactivateMovie_shouldThrowException_whenMovieNotFound() {
        // given
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        // when + then
        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> adminService.deactivateMovie(1L)
        );
    }

    @Test
    @DisplayName("activateMovie - 활성화 처리 성공")
    void activateMovie_success() {
        Long movieId = 1L;
        Movie movie = new Movie(); // 기본적으로 isUse = true
        movie.deactivate(); // false로 만들고 시작

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        adminService.activateMovie(movieId);

        assertThat(movie.isUse()).isTrue();
        verify(movieRepository).save(movie);
    }

    @Test
    @DisplayName("activateMovie - 영화가 존재하지 않으면 예외 발생")
    void activateMovie_notFound() {
        // given
        Long movieId = 2L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> adminService.activateMovie(movieId));
    }
}
