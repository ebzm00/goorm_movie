package com.shakkib.netflixclone.jwt;

import com.shakkib.netflixclone.dtoes.CustomUserDetails;
import com.shakkib.netflixclone.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //헤더
        String authorizationHeader = request.getHeader("Authorization");

        //헤더가 null이 아니고 Bearer로 시작
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //"Bearer " 다음 값
        String token = authorizationHeader.split(" ")[1];

        //토큰 만료 검증
        if(jwtUtil.isTokenExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        User user = new User();

        Member member = new Member();
        member.setEmail(email);
        member.setPassword("tempassword");
        member.setRole(UserRole.valueOf(role));

//        if (admin != null) {
//            // Admin인 경우
//            customUserDetails = new CustomUserDetails(admin);
//        } else {
//            // 일반 사용자인 경우
//            customUserDetails = new CustomUserDetails(user);
//        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user,);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //요청이 끝날 때 까지만 지속되는 임시 세션
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //다음 필터로 전달
        filterChain.doFilter(request, response);


    }
}

