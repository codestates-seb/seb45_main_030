package com.mainproject.grilledshrimp.global.auth.config;

import com.mainproject.grilledshrimp.global.auth.filter.JwtAuthenticationFilter;
import com.mainproject.grilledshrimp.global.auth.jwt.JwtTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin() // 동일 출처로부터 들어오는 request만 허용
                .and()
                .csrf().disable()        // CSRF 공격 방지 비활성화
                .cors(withDefaults())    // CORS 허용
                .formLogin().disable()   // form 기반 로그인 비활성화
                .httpBasic().disable()   // http 기본 인증 비활성화
                .logout()               // 로그아웃 설정
                .logoutUrl("/users/logout")   // 로그아웃 url 설정
                .and()
                .apply(new CustomFilterConfigurer()) // 커스텀 필터 적용
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/post/**").hasRole("USER")   // /post/** 경로에 대해서는 USER 권한이 있어야 접근 가능
                        .anyRequest().permitAll()    // 그 외 나머지 요청에 대해서는 인증 없이 접근 허용
                );
        return http.build();
    }

    //
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));    //
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));  //

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();   //
        source.registerCorsConfiguration("/**", configuration);      //
        return source;
    }

    // 패스워드 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            customFilter.setFilterProcessesUrl("/users/login");
            http.addFilterBefore(customFilter, JwtAuthenticationFilter.class);
        }
    }
}
