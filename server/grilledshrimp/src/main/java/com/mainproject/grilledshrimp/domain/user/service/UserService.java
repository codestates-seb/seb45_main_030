package com.mainproject.grilledshrimp.domain.user.service;

import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import com.mainproject.grilledshrimp.domain.user.utils.UserAuthorityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserAuthorityUtils authorityUtils;

    private final RedisTemplate<String, Object> redisTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserAuthorityUtils authorityUtils, RedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
        this.redisTemplate = redisTemplate;
    }

    // 패스워드를 암호화 해서 회원가입을 진행합니다.
    public Users createUser(Users user) {
        // 이미 있는 유저라면 에러를 발생시킵니다.


        user.setModifiedAt(LocalDateTime.now()); // 현재 시간으로 수정 시간을 설정합니다.

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // role을 유저에 맞게 설정합니다.
        List<String> roles = authorityUtils.createRoles(user.getEmail());
        user.setRole(roles);

        Users savedUser = userRepository.save(user);
        log.info("유저 생성");
        return savedUser;
    }

    public Users findUser(long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    public void logoutUser(String email) {
        if(redisTemplate.opsForValue().get("JWT_TOKEN:" + email) == null) {
            log.info("이미 로그아웃된 유저");
            throw new BusinessLogicException(ExceptionCode.USER_LOGOUTED);
        }
        redisTemplate.delete("JWT_TOKEN:" + email);
        log.info("유저 : {} 로그아웃", email);
    }
}
