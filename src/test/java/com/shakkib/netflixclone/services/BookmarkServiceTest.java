package com.shakkib.netflixclone.services;


import com.shakkib.netflixclone.dtoes.BookMarkDTO;
import com.shakkib.netflixclone.entity.BookMark;
import com.shakkib.netflixclone.entity.Movie;
import com.shakkib.netflixclone.entity.Role;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.repository.BookmarkRepository;
import com.shakkib.netflixclone.repository.MovieRepository;
import com.shakkib.netflixclone.repository.UserRepository;
import com.shakkib.netflixclone.services.impl.BookmarkService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookmarkServiceTest {

    @InjectMocks
    private BookmarkService bookmarkService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    private User user;
    private Movie movie;
    private List<BookMark> bookmarks;
    private String email = "test";
    private Long movieId = 1L;

    @BeforeEach
    public void setUp() {

        user = new User("test", Role.USER);
        movie = new Movie(movieId,"mockTestMovie");

        BookMark bookmark = new BookMark(user, movie);
        bookmarks = List.of(bookmark);

    }

    @Test //북마크 등록 테스트
    @DisplayName("북마크 등록 성공")
    void addBookmarkSuccess(){
        // Given (Mock 데이터 설정)
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        // When (북마크 추가 실행)
        boolean result = bookmarkService.addBookmark(email, movieId);

        // Then (검증)
        Assertions.assertTrue(result);
        Assertions.assertEquals("test", user.getEmail());
        verify(bookmarkRepository, times(1)).save(any(BookMark.class));
        verify(userRepository, times(1)).findUserByEmail(anyString());
    }

    @Test
    @DisplayName("북마크 등록 실패 - 유저 없음")
    void addBookmarkFail_UserNotFound() {
        // Given (유저가 없음)
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        // When
        boolean result = bookmarkService.addBookmark(email, movieId);

        System.out.println(result);

        // Then
        Assertions.assertFalse(result);
        verify(bookmarkRepository, never()).save(any(BookMark.class)); // 저장되지 않아야 함
    }

    @Test
    @DisplayName("북마크 등록 실패 - 존재하지 않는 영화")
    void addBookmarkFail_MovieNotFound() {
        // Given (영화가 없음)
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        when(movieRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        boolean result = bookmarkService.addBookmark(email, movieId);

        System.out.println(result);

        // Then
        Assertions.assertFalse(result);
        verify(bookmarkRepository, never()).save(any(BookMark.class)); // 저장되지 않아야 함
    }

    @Test
    @DisplayName("북마크 삭제 성공")
    void deleteBookmarkSuccess() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        // When
        boolean result = bookmarkService.deleteBookmark(email, movieId);

        // Then
        Assertions.assertTrue(result);
        verify(bookmarkRepository, times(1)).deleteByUserAndMovie(user, movie);
    }

    @Test
    @DisplayName("북마크 삭제 실패 - 유저 없음")
    void deleteBookmarkFail_UserNotFound() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        // When
        boolean result = bookmarkService.deleteBookmark(email, movieId);

        // Then
        Assertions.assertFalse(result);
        verify(bookmarkRepository, never()).deleteByUserAndMovie(any(), any());
    }

    @Test
    @DisplayName("북마크 삭제 실패 - 영화 없음")
    void deleteBookmarkFail_MovieNotFound() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        // When
        boolean result = bookmarkService.deleteBookmark(email, movieId);

        // Then
        Assertions.assertFalse(result);
        verify(bookmarkRepository, never()).deleteByUserAndMovie(any(), any());
    }

    @Test
    @DisplayName("북마크 조회 성공")
    void getBookmarks_Success() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(bookmarkRepository.findAllByUser(user)).thenReturn(bookmarks);

        // When
        List<BookMarkDTO.Response> result = bookmarkService.getBookmarks(email);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size()); // 북마크 개수 검증
        Assertions.assertEquals(movie.getTitle(), result.get(0).getTitle()); // DTO 변환 확인
    }

    @Test
    @DisplayName("북마크 조회 실패 - 유저 없음")
    void getBookmarks_Fail_UserNotFound() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        // When
        List<BookMarkDTO.Response> result = bookmarkService.getBookmarks(email);

        // Then
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("북마크 조회 성공 - 북마크가 없는 경우")
    void getBookmarks_Success_NoBookmarks() {
        // Given
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(bookmarkRepository.findAllByUser(user)).thenReturn(Collections.emptyList());

        // When
        List<BookMarkDTO.Response> result = bookmarkService.getBookmarks(email);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty()); // 빈 리스트 확인
    }
}
