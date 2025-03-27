package com.shakkib.netflixclone.services;

import com.shakkib.netflixclone.entity.RefreshToken;
import com.shakkib.netflixclone.jwt.JwtUtil;
import com.shakkib.netflixclone.repository.RefreshTokenRepository;
import com.shakkib.netflixclone.services.impl.ReissueService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReissueServiceTest {

    @InjectMocks
    private ReissueService reissueService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Cookie cookie;

    private final String validRefreshToken = "validRefreshToken";
    private final String expiredRefreshToken = "expiredRefreshToken";
    private final String invalidCategoryToken = "invalidCategoryToken";
    private final String userEmail = "test@example.com";
    private final String userRole = "USER";

    @BeforeEach
    void setup() {
        // JWT 유틸 mock 기본 설정
        lenient().when(jwtUtil.getEmail(validRefreshToken)).thenReturn(userEmail);
        lenient().when(jwtUtil.getRole(validRefreshToken)).thenReturn(userRole);
        lenient().when(jwtUtil.getCategory(validRefreshToken)).thenReturn("refresh");
        lenient().when(jwtUtil.getCategory(invalidCategoryToken)).thenReturn("access"); // 잘못된 타입
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void reissueToken_Success() {
        // Given
        Cookie[] cookies = { new Cookie("refresh", validRefreshToken) };
        when(request.getCookies()).thenReturn(cookies);
        when(jwtUtil.isTokenExpired(validRefreshToken)).thenReturn(false);
        when(refreshTokenRepository.existsByRefreshToken(validRefreshToken)).thenReturn(true);
        when(jwtUtil.createJwtToken(eq("access"), anyString(), anyString(), anyLong())).thenReturn("newAccessToken");
        when(jwtUtil.createJwtToken(eq("refresh"), anyString(), anyString(), anyLong())).thenReturn("newRefreshToken");

        // When
        ResponseEntity<?> responseEntity = reissueService.reissue(request, response);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(refreshTokenRepository, times(1)).deleteByRefreshToken(validRefreshToken);
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("토큰이 없을 때 실패")
    void reissueToken_Fail_NoToken() {
        // Given
        when(request.getCookies()).thenReturn(null);

        // When
        ResponseEntity<?> responseEntity = reissueService.reissue(request, response);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("refresh token null", responseEntity.getBody());
    }

    @Test
    @DisplayName("토큰 만료로 인한 실패")
    void reissueToken_Fail_ExpiredToken() {
        // Given
        Cookie[] cookies = { new Cookie("refresh", expiredRefreshToken) };
        when(request.getCookies()).thenReturn(cookies);
        doThrow(new ExpiredJwtException(null, null, "Token Expired")).when(jwtUtil).isTokenExpired(expiredRefreshToken);

        // When
        ResponseEntity<?> responseEntity = reissueService.reissue(request, response);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("refresh token expired", responseEntity.getBody());
    }

    @Test
    @DisplayName("토큰 타입이 refresh가 아닐 경우 실패")
    void reissueToken_Fail_InvalidCategory() {
        // Given
        Cookie[] cookies = { new Cookie("refresh", invalidCategoryToken) };
        when(request.getCookies()).thenReturn(cookies);
        when(jwtUtil.isTokenExpired(invalidCategoryToken)).thenReturn(false);
        when(jwtUtil.getCategory(invalidCategoryToken)).thenReturn("access"); // refresh가 아님

        // When
        ResponseEntity<?> responseEntity = reissueService.reissue(request, response);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("invalid refresh token", responseEntity.getBody());
    }

    @Test
    @DisplayName("DB에 토큰이 존재하지 않을 경우 실패")
    void reissueToken_Fail_TokenNotExist() {
        // Given
        Cookie[] cookies = { new Cookie("refresh", validRefreshToken) };
        when(request.getCookies()).thenReturn(cookies);
        when(jwtUtil.isTokenExpired(validRefreshToken)).thenReturn(false);
        when(jwtUtil.getCategory(validRefreshToken)).thenReturn("refresh");
        when(refreshTokenRepository.existsByRefreshToken(validRefreshToken)).thenReturn(false); // 존재하지 않음

        // When
        ResponseEntity<?> responseEntity = reissueService.reissue(request, response);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("refresh token does not exist", responseEntity.getBody());
    }
}
