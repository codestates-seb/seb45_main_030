package com.mainproject.grilledshrimp.global.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainproject.grilledshrimp.domain.user.dto.UserLoginDto;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.global.auth.jwt.JwtTokenizer;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// JWT를 이용한 인증을 위한 필터
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
    }

    @SneakyThrows // 예외를 무시하고 실행
    @Override
    // 인증을 위한 토큰 생성
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserLoginDto userLoginDto = objectMapper.readValue(request.getInputStream(), UserLoginDto.class);

        // 인증을 위한 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    // 인증이 성공했을 때 JWT 토큰을 생성해서 응답 헤더에 추가
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        Users users = (Users) authResult.getPrincipal();

        String accessToken = delegateAccessToken(users);
        String refreshToken = delegateRefreshToken(users);

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);
    }

    // access token 생성
    private String delegateAccessToken(Users users) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", users.getEmail());
        claims.put("roles", users.getRole());

        String subject = users.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    // refresh token 생성
    private String delegateRefreshToken(Users users) {
        String subject = users.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
