//package com.shakkib.netflixclone.Movie;
//
//package com.shakkib.netflixclone.controllers;
//
//import com.shakkib.netflixclone.controllers.MovieController;
//import com.shakkib.netflixclone.dtoes.MovieListDTO;
//import com.shakkib.netflixclone.services.MovieService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(MovieController.class)
//class MovieControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MovieService movieService;
//
//    @Test
//    void testGetAllMovies() throws Exception {
//        // 가짜 데이터(Mock 데이터)
//        List<MovieListDTO> movies = List.of(
//                new MovieListDTO(101L, "어벤져스", "/images/avengers.jpg"),
//                new MovieListDTO(102L, "아이언맨", "/images/ironman.jpg")
//        );
//
//        // MovieService의 getAllMovies() 호출 시 위 데이터를 반환하도록 설정
//        when(movieService.getAllMovies()).thenReturn(movies);
//
//        // MockMvc를 사용하여 API 호출 테스트
//        mockMvc.perform(get("/movies")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())  // HTTP 200 OK 기대
//                .andExpect(jsonPath("$.size()", is(2)))  // JSON 배열 크기 확인
//                .andExpect(jsonPath("$[0].title", is("어벤져스")))  // 첫 번째 영화 제목 확인
//                .andExpect(jsonPath("$[1].posterPath", is("/images/ironman.jpg")));  // 두 번째 영화 포스터 확인
//    }
//}
