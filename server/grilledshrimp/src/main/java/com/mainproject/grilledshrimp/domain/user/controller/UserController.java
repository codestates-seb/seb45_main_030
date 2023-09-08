package com.mainproject.grilledshrimp.domain.user.controller;

import com.mainproject.grilledshrimp.domain.user.dto.UserLoginDto;
import com.mainproject.grilledshrimp.domain.user.dto.UserNamePatchDto;
import com.mainproject.grilledshrimp.domain.user.dto.UserPostDto;
import com.mainproject.grilledshrimp.domain.user.dto.UserProfileImageDto;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.mapper.UserMapper;
import com.mainproject.grilledshrimp.domain.user.service.AuthUserDetailsService;
import com.mainproject.grilledshrimp.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthUserDetailsService authUserDetailsService;
    private final UserMapper mapper;

    public UserController(UserService userService, AuthUserDetailsService authUserDetailsService, UserMapper mapper) {
        this.userService = userService;
        this.authUserDetailsService = authUserDetailsService;
        this.mapper = mapper;
    }


    @PostMapping("/signup")
    public ResponseEntity postUser(@Valid @RequestBody UserPostDto userPostDto) {
        Users users = userService.createUser(mapper.userPostDtoToUser(userPostDto));
        return new ResponseEntity(mapper.userToUserResponseDto(users), HttpStatus.CREATED);
    }

    // 유저 사진 등록
    @PostMapping("/{user-id}/image")
    public ResponseEntity postUserImage(@Valid @RequestBody UserProfileImageDto userProfileImageDto) {

        return new ResponseEntity(HttpStatus.OK);
    }

    // 유저 사진 수정
    @PatchMapping("/{user-id}/image")
    public ResponseEntity patchUserImage(@Valid @RequestBody UserProfileImageDto userProfileImageDto) {

        return new ResponseEntity(HttpStatus.OK);
    }

    // 유저 사진 삭제도 있어야 할 듯

    // 유저 이름 수정
    // 유저 정보 수정DTO에 수정할 정보 여러개 넣고 필요한 값만 받아 바꾸면 됨
    @PatchMapping("/{user-id}")
    public ResponseEntity patchUserName(@Valid @RequestBody UserNamePatchDto userNamePatchDto) {

        return new ResponseEntity(HttpStatus.OK);
    }

    // 특정 유저의 전체 게시글 가져오기
    @GetMapping("/posts")
    public ResponseEntity getUserPosts() {

        return new ResponseEntity(HttpStatus.OK);
    }

    // 특정 유저의 정보 가져오기
    @GetMapping("/{user-id}")
    public ResponseEntity getUserInfo() {

        return new ResponseEntity(HttpStatus.OK);
    }

    // 모든 유저 정보 가져오기
    @GetMapping
    public ResponseEntity getAllUserInfo() {

        return new ResponseEntity(HttpStatus.OK);
    }

    // 특정 유저 삭제
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser() {

        return new ResponseEntity(HttpStatus.OK);
    }
}
