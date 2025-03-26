package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.MovieDTO;
import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.entity.Genre;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.services.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@WithMockUser(username = "testUser", roles = {"USER"})
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    private Movie createTestMovie(String title) {
        return new Movie(
                1L, 100L, title, "Original " + title, "poster.jpg", false,
                "overview", LocalDate.now(), new Genre(1L, "Action"),
                LocalDateTime.now(), LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("전체 영화 목록 조회 - 성공")
    void getAllMovies_returnsList() throws Exception {
        Movie movie = createTestMovie("Test Movie");
        List<MovieListDTO> movies = List.of(new MovieListDTO(movie));
        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Movie"));
    }

    @Test
    @DisplayName("타이틀로 영화 조회 - 성공")
    void getMovieByTitle_returnsMovie() throws Exception {
        Movie movie = createTestMovie("Title Movie");
        when(movieService.getMovieByTitle("Title Movie")).thenReturn(Optional.of(new MovieDTO(movie)));

        mockMvc.perform(get("/movies/title").param("title", "Title Movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title Movie"));
    }

    @Test
    @DisplayName("타이틀로 영화 조회 - 실패(존재하지 않음)")
    void getMovieByTitle_returnsNotFound() throws Exception {
        when(movieService.getMovieByTitle("Title Movie")).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies/title").param("title", "Title Movie"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("장르로 영화 조회 - 성공")
    void getMoviesByGenre_returnsList() throws Exception {
        Movie movie = createTestMovie("Genre Movie");
        List<MovieListDTO> movies = List.of(new MovieListDTO(movie));
        when(movieService.getMoviesByGenre("Action")).thenReturn(movies);

        mockMvc.perform(get("/movies/genre").param("name", "Action"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Genre Movie"));
    }

    @Test
    @DisplayName("장르로 영화 조회 - 실패(빈 결과)")
    void getMoviesByGenre_returnsNotFound() throws Exception {
        when(movieService.getMoviesByGenre("Fantasy")).thenReturn(List.of());

        mockMvc.perform(get("/movies/genre").param("name", "Fantasy"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("키워드로 영화 조회 - 성공")
    void searchMoviesByKeyword_returnsList() throws Exception {
        Movie movie = createTestMovie("아이언맨");
        List<MovieListDTO> movies = List.of(new MovieListDTO(movie));
        when(movieService.searchMoviesByKeyword("아")).thenReturn(movies);

        mockMvc.perform(get("/movies/search").param("keyword", "아"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("아이언맨"));
    }

    @Test
    @DisplayName("키워드로 영화 조회 - 실패(결과 없음)")
    void searchMoviesByKeyword_returnsNotFound() throws Exception {
        when(movieService.searchMoviesByKeyword("없는키워드")).thenReturn(List.of());

        mockMvc.perform(get("/movies/search").param("keyword", "없는키워드"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("영화 ID로 상세 조회 - 성공")
    void getMovieById_returnsMovie() throws Exception {
        Movie movie = createTestMovie("Detail Movie");
        when(movieService.getMovieById(1L)).thenReturn(Optional.of(new MovieDTO(movie)));

        mockMvc.perform(get("/movies/detail/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Detail Movie"));
    }

    @Test
    @DisplayName("영화 ID로 상세 조회 - 실패(없음)")
    void getMovieById_returnsNotFound() throws Exception {
        when(movieService.getMovieById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies/detail/1"))
                .andExpect(status().isNotFound());
    }
}
