package com.mainproject.grilledshrimp.global.auth.jwt.filter;

import com.mainproject.grilledshrimp.domain.user.utils.UserAuthorityUtils;
import com.mainproject.grilledshrimp.global.auth.jwt.JwtTokenizer;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

// JWT를 이용한 인증을 위한 필터
@Slf4j
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final UserAuthorityUtils userAuthorityUtils;

    private final RedisTemplate<String, Object> redisTemplate;

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer, UserAuthorityUtils userAuthorityUtils, RedisTemplate<String, Object> redisTemplate) {
        this.jwtTokenizer = jwtTokenizer;
        this.userAuthorityUtils = userAuthorityUtils;
        this.redisTemplate = redisTemplate;
    }

    // JWT 토큰을 검증하고, 토큰이 정상적이라면 SecurityContext에 인증 정보를 저장
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }


    @Override
    // 토큰이 없거나, Bearer로 시작하지 않는 경우 필터를 수행하지 않음
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null)
        {
            return true;
        }
        if (!authorization.startsWith("Bearer "))
        {
            return true;
        }
        if(jwtTokenizer.validateToken(authorization.replace("Bearer ", ""))) {
            String key = "JWT_TOKEN:" + jwtTokenizer.getUsername(authorization.replace("Bearer ", ""));
            if(redisTemplate.opsForValue().get(key) == null) {
                log.info("이미 로그아웃된 유저");
                throw new BusinessLogicException(ExceptionCode.USER_LOGOUTED);
            }
            // 키는 있지만 값이 다르면 로그아웃된 유저
            else if(!redisTemplate.opsForValue().get(key).equals(authorization.replace("Bearer ", ""))) {
                log.info("로그아웃 키가 다름 {} / {}", redisTemplate.opsForValue().get(key), authorization.replace("Bearer ", ""));
                throw new BusinessLogicException(ExceptionCode.AUTHENTICATION_FAILED);
            }
        }
        return false;
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", ""); // (3-1)
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // (3-2)
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();   // (3-3)

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");   // (4-1)
        List<GrantedAuthority> authorities = userAuthorityUtils.createAuthorities((List)claims.get("roles"));  // (4-2)
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);  // (4-3)
        SecurityContextHolder.getContext().setAuthentication(authentication); // (4-4)
        log.info(authentication.getAuthorities().toString());
    }
}
