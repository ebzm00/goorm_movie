package com.shakkib.netflixclone.controllers;

import com.shakkib.netflixclone.dtoes.BookMarkDTO;
import com.shakkib.netflixclone.dtoes.CustomUserDetails;
import com.shakkib.netflixclone.entity.BookMark;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.entity.Role;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.services.impl.BookmarkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookmarkService bookmarkService;

    private CustomUserDetails customUserDetails;

    private List<BookMarkDTO.Response> bookmarks;

    private final Long movieId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user = new User("test", Role.USER);
        Movie movie = new Movie(movieId,"mockTestMovie");

        BookMark bookmark = new BookMark(user, movie);

        BookMarkDTO.Response response = new BookMarkDTO.Response(bookmark);
        bookmarks = List.of(response);

        customUserDetails = new CustomUserDetails(user);

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        context.setAuthentication(authentication);

    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("북마크 등록 성공")
    void addBookmarkTest_Success() throws Exception {
        Long movieId = 1L;
        when(bookmarkService.addBookmark(any(String.class),any(Long.class))).thenReturn(true);

        mockMvc.perform(get("/bookmark/{movieId}", movieId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()); // HTTP 200 응답 확인

        verify(bookmarkService, times(1)).addBookmark("test", movieId);
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("북마크 등록 실패 - 유저 없음")
    void addBookmarkTest_Fail_UserNotFound() throws Exception {
        Long movieId = 1L;

        // Given: 유저가 존재하지 않는 경우
        when(bookmarkService.addBookmark(any(String.class), any(Long.class))).thenReturn(false);

        // When & Then
        mockMvc.perform(get("/bookmark/{movieId}", movieId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest()); // HTTP 400
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("북마크 삭제 성공")
    void deleteBookmarkTest_Success() throws Exception {
        Long movieId = 1L;
        when(bookmarkService.deleteBookmark(any(String.class),any(Long.class))).thenReturn(true);

        mockMvc.perform(delete("/bookmark/{movieId}", movieId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()); // HTTP 200 응답 확인

        verify(bookmarkService, times(1)).deleteBookmark("test", movieId);
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("북마크 삭제 실패 - 북마크 없음")
    void deleteBookmarkTest_Fail_BookmarkNotFound() throws Exception {
        Long movieId = 1L;

        // Given: 삭제할 북마크가 존재하지 않는 경우
        when(bookmarkService.deleteBookmark(any(String.class), any(Long.class))).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/bookmark/{movieId}", movieId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest()); // HTTP 404 응답 확인
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("북마크 조회 성공")
    void getAllBookmarkTest_Success() throws Exception {
        Long movieId = 1L;
        when(bookmarkService.getBookmarks(any(String.class))).thenReturn(bookmarks);

        mockMvc.perform(get("/bookmark/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()); // HTTP 200 응답 확인

        verify(bookmarkService, times(1)).getBookmarks("test");
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("북마크 조회 실패 - 유저 없음")
    void getAllBookmarkTest_Fail_UserNotFound() throws Exception {
        // Given: 유저가 존재하지 않는 경우
        when(bookmarkService.getBookmarks(any(String.class))).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/bookmark/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()) // HTTP 200 응답은 유지됨
                .andExpect(jsonPath("$.length()").value(0));

        verify(bookmarkService, times(1)).getBookmarks("test");
    }
}
