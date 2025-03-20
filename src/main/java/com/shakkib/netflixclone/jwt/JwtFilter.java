package com.shakkib.netflixclone.jwt;

import com.shakkib.netflixclone.dtoes.CustomUserDetails;
import com.shakkib.netflixclone.entity.Role;
import com.shakkib.netflixclone.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        //헤더
//        String authorizationHeader = request.getHeader("Authorization");
//
//        //헤더가 null이 아니고 Bearer로 시작
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        //"Bearer " 다음 값
//        String token = authorizationHeader.split(" ")[1];
//
//        //토큰 만료 검증
//        if(jwtUtil.isTokenExpired(token)) {
//
//            System.out.println("token expired");
//            filterChain.doFilter(request, response);
//
//            return;
//        }
//
//        String email = jwtUtil.getEmail(token);
//        String role = jwtUtil.getRole(token);
//
//        User user = new User(email, Role.valueOf(role));
//
//        CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//
//        //요청이 끝날 때 까지만 지속되는 임시 세션
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        //다음 필터로 전달
//        filterChain.doFilter(request, response);
//
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isTokenExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

// 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

// username, role 값을 획득
        String email = jwtUtil.getEmail(accessToken);
        String role = jwtUtil.getRole(accessToken);

        User user = new User(email, Role.valueOf(role));

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }


}

