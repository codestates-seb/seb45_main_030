package com.mainproject.grilledshrimp.global.auth.config;

import com.mainproject.grilledshrimp.global.auth.jwt.handler.UserAccessDeniedHandler;
import com.mainproject.grilledshrimp.global.auth.jwt.handler.UserAuthenticationEntryPoint;
import com.mainproject.grilledshrimp.global.utils.UserAuthorityUtils;
import com.mainproject.grilledshrimp.global.auth.jwt.filter.JwtAuthenticationFilter;
import com.mainproject.grilledshrimp.global.auth.jwt.filter.JwtVerificationFilter;
import com.mainproject.grilledshrimp.global.auth.jwt.handler.UserAuthenticationFailureHandler;
import com.mainproject.grilledshrimp.global.auth.jwt.handler.UserAuthenticationSuccessHandler;
import com.mainproject.grilledshrimp.global.auth.jwt.JwtTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final UserAuthorityUtils authorityUtils;

    private final RedisTemplate<String, Object> redisTemplate;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, UserAuthorityUtils authorityUtils, RedisTemplate<String, Object> redisTemplate) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.redisTemplate = redisTemplate;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()        // CSRF 공격 방지 비활성화
                .cors(withDefaults())    // CORS 허용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)    // 세션 비활성화
                .and()
                .formLogin().disable()   // form 기반 로그인 비활성화
                .httpBasic().disable()   // http 기본 인증 비활성화
                .exceptionHandling()
                .authenticationEntryPoint(new UserAuthenticationEntryPoint())   // 인증 실패 시 처리
                .accessDeniedHandler(new UserAccessDeniedHandler())   // 인가 실패 시 처리
                .and()
                .apply(new CustomFilterConfigurer()) // 커스텀 필터 적용
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/post/**").hasRole("USER")   // /post/** 경로에 대해서는 USER 권한이 있어야 접근 가능
                        .anyRequest().permitAll()    // 그 외 나머지 요청에 대해서는 인증 없이 접근 허용
                );
        return http.build();
    }

    // 커스텀 필터 적용
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 패스워드 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 커스텀 필터
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            customFilter.setFilterProcessesUrl("/users/login");
            customFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
            customFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, redisTemplate);
            http
                    .addFilter(customFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}
