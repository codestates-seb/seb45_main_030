package com.mainproject.grilledshrimp.domain.user.service;

import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import com.mainproject.grilledshrimp.global.utils.UserAuthorityUtils;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

// db에 저장된 유저 정보를 가져와서 인증을 처리하는 클래스
@Service
public class AuthUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserAuthorityUtils authorityUtils;

    public AuthUserDetailsService(UserRepository userRepository, UserAuthorityUtils authorityUtils) {
        this.userRepository = userRepository;
        this.authorityUtils = authorityUtils;
    }

    // 유저 정보를 가져와서 UserDetails 객체로 변환합니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = userRepository.findByEmail(username);
        Users user = optionalUsers.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return new AuthUserDetails(user);
    }

    private final class AuthUserDetails extends Users implements UserDetails {
        public AuthUserDetails(Users user) {
            setUserId(user.getUserId());
            setEmail(user.getEmail());
            setPassword(user.getPassword());
            setUsername(user.getUsername());
            setRole(user.getRole());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRole());
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
