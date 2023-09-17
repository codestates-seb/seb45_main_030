package com.mainproject.grilledshrimp.domain.user.service;

import com.mainproject.grilledshrimp.domain.post.dto.PostsResponseDto;
import com.mainproject.grilledshrimp.domain.post.dto.PostsResponseSimpleDto;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.mapper.PostsMapper;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
import com.mainproject.grilledshrimp.domain.user.dto.UserPatchDto;
import com.mainproject.grilledshrimp.domain.user.dto.UserUpdatePasswordDto;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import com.mainproject.grilledshrimp.global.image.AwsS3Service;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import com.mainproject.grilledshrimp.domain.user.utils.UserAuthorityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final AwsS3Service awsS3Service;
    private final UserRepository userRepository;
    private final PostsMapper postsMapper;
    private final PostsRepository postsRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserAuthorityUtils authorityUtils;

    private final RedisTemplate<String, Object> redisTemplate;

    public UserService(AwsS3Service awsS3Service, UserRepository userRepository, PostsMapper postsMapper, PostsRepository postsRepository, PasswordEncoder passwordEncoder, UserAuthorityUtils authorityUtils, RedisTemplate redisTemplate) {
        this.awsS3Service = awsS3Service;
        this.userRepository = userRepository;
        this.postsMapper = postsMapper;
        this.postsRepository = postsRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
        this.redisTemplate = redisTemplate;
    }


    // 패스워드를 암호화 해서 회원가입을 진행합니다.
    public Users createUser(Users user) {
        // 이미 있는 유저라면 에러를 발생시킵니다.
        verifyExistsEmail(user.getEmail());

        user.setModifiedAt(LocalDateTime.now()); // 현재 시간으로 수정 시간을 설정합니다.

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // role을 유저에 맞게 설정합니다.
        List<String> roles = authorityUtils.createRoles(user.getEmail());
        user.setRole(roles);
        user.setCreatedAt(LocalDateTime.now());
        user.setUserStatus(Users.UserStatus.USER_ACTIVE);

        Users savedUser = userRepository.save(user);
        log.info("유저 생성");
        return savedUser;
    }

    public Users uploadImage(long userId, MultipartFile file){
        Users user = userRepository.findById(userId).orElseThrow();
        String fileName = awsS3Service.uploadImage(file);
        user.setProfileImage(fileName);
        return userRepository.save(user);
    }

    public Users findUser(long userId) {
        Users findUser = findVerifiedUser(userId);
        return findVerifiedUser(userId);
    }

    public Users findUserByUserName(String userName) {
        Optional<Users> findUser = userRepository.findByUsername(userName);
        return findUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    public void updateUserPassword(UserUpdatePasswordDto userUpdatePasswordDto) {
        String username = userUpdatePasswordDto.getUsername();
        String email = userUpdatePasswordDto.getEmail();
        String password = userUpdatePasswordDto.getNewPassword();

        Users findUser = findVerifiedUserNameAndEmail(username, email);

        findUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(findUser);
    }

    public List<Users> findAllUser() {
        return userRepository.findAll();
    }

    public void updateProfileImage(long userId, String profileImage) {
        Users user = findVerifiedUser(userId);
        user.setProfileImage(profileImage);
        userRepository.save(user);
    }

    // 유저 정보 수정
    public Users updateUser(Long userId, UserPatchDto userPatchDto) {
        Users findUser = findVerifiedUser(userId);
        Optional.ofNullable(userPatchDto.getUsername()).ifPresent(findUser::setUsername);
        Optional.ofNullable(userPatchDto.getIntroduction()).ifPresent(findUser::setIntroduction);
        Optional.ofNullable(userPatchDto.getProfile_image()).ifPresent(findUser::setProfileImage);
        Optional.ofNullable(userPatchDto.getIntroduction());

        findUser.setModifiedAt(LocalDateTime.now());
        return userRepository.save(findUser);
    }

    // 유저 로그아웃
    public void logoutUser(String email) {
        if(redisTemplate.opsForValue().get("JWT_TOKEN:" + email) == null) {
            log.info("이미 로그아웃된 유저");
            throw new BusinessLogicException(ExceptionCode.USER_LOGOUTED);
        }
        redisTemplate.delete("JWT_TOKEN:" + email);
        log.info("유저 : {} 로그아웃", email);
    }

    // 특정 유저 삭제
    public void deleteUser(long userId) {
        Users findUser = findVerifiedUser(userId);
        userRepository.delete(findUser);
    }

    // 유저 이름과 이메일 검증하기
    public Users findVerifiedUserNameAndEmail(String username, String email) {
        Optional<Users> user = userRepository.findByUsernameAndEmail(username, email);
        Users findUser = user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        log.info("유저 검증 완료");
        return findUser;
    }


    // 특정 유저가 존재하는지 확인
    public Users findVerifiedUser(long userId) {
        Optional<Users> optionalUser = userRepository.findById(userId);
        Users findUser = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser;
    }

    // 특정 유저가 없는지 확인
    public void verifyExistsEmail(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        if (user.isPresent())
            throw new BusinessLogicException(ExceptionCode.USER_EXIST);
    }

    public List<PostsResponseSimpleDto> findUserPosts(long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return postsMapper.postsToPostsResponseSimpleDtos(postsRepository.findByUsers_UserId(userId));
    }
}
