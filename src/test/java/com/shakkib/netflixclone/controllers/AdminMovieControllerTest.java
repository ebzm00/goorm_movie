package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.MovieListDTO;
import com.shakkib.netflixclone.entity.Genre;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.services.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminMovieController.class)
@AutoConfigureMockMvc(addFilters = false) // 시큐리티 필터 제거
class AdminMovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminService adminService;

    private Movie createTestMovie(String title) {
        return new Movie(
                1L,
                101L,
                title,
                "Original Title",
                "/poster.jpg",
                false,
                "Overview",
                LocalDate.now(),
                new Genre(1L, "액션"),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("관리자 - 전체 영화 목록 조회")
    void getAllMoviesForAdmin() throws Exception {
        Movie movie = createTestMovie("Admin Movie");
        List<MovieListDTO> movies = List.of(new MovieListDTO(movie));

        when(adminService.getAllMoviesIncludingInactive()).thenReturn(movies);

        mockMvc.perform(get("/admin/movies/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Admin Movie"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("관리자 - 영화 비활성화 성공")
    void deactivateMovie_success() throws Exception {
        Long movieId = 1L;
        doNothing().when(adminService).deactivateMovie(movieId);

        mockMvc.perform(put("/admin/movies/{movieId}/deactivate", movieId))
                .andExpect(status().isOk())
                .andExpect(content().string("영화가 비활성화되었습니다."));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("관리자 - 영화 비활성화 실패 (존재하지 않음)")
    void deactivateMovie_fail() throws Exception {
        Long movieId = 1L;
        doThrow(new RuntimeException()).when(adminService).deactivateMovie(movieId);

        mockMvc.perform(put("/admin/movies/{movieId}/deactivate", movieId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("해당 영화를 찾을 수 없습니다."));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("관리자 - 영화 활성화 성공")
    void activateMovie_success() throws Exception {
        Long movieId = 1L;
        doNothing().when(adminService).activateMovie(movieId);

        mockMvc.perform(put("/admin/movies/{movieId}/activate", movieId))
                .andExpect(status().isOk())
                .andExpect(content().string("영화가 다시 활성화되었습니다."));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("관리자 - 영화 활성화 실패 (존재하지 않음)")
    void activateMovie_fail() throws Exception {
        Long movieId = 1L;
        doThrow(new RuntimeException()).when(adminService).activateMovie(movieId);

        mockMvc.perform(put("/admin/movies/{movieId}/activate", movieId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("해당 영화를 찾을 수 없습니다."));
    }
}
