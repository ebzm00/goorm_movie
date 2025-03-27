package com.shakkib.netflixclone.config;

import com.shakkib.netflixclone.jwt.LogoutFilter;
import com.shakkib.netflixclone.jwt.JwtFilter;
import com.shakkib.netflixclone.jwt.JwtUtil;
import com.shakkib.netflixclone.jwt.LoginFilter;
import com.shakkib.netflixclone.repository.RefreshTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll());

        http
                .addFilterBefore(new JwtFilter(jwtUtil),LoginFilter.class);

        //세션 설정
        http
                .sessionManagement((session)-> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), refreshTokenRepository,jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http
                .addFilterBefore(new LogoutFilter(refreshTokenRepository,jwtUtil), org.springframework.security.web.authentication.logout.LogoutFilter.class);
        return http.build();
    }
}