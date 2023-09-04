package com.mainproject.grilledshrimp.global.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin() // 동일 출처로부터 들어오는 request만 허용
                .and()
                .csrf().disable()        // CSRF 공격 방지 비활성화
                .cors(withDefaults())    // CORS 허용
                .formLogin().disable()   // form 기반 로그인 비활성화
                .httpBasic().disable()   // http 기본 인증 비활성화
                .authorizeHttpRequests(authorize -> authorize
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
}
