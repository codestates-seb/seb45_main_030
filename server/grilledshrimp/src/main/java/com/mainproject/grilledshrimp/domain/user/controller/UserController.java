package com.mainproject.grilledshrimp.domain.user.controller;

import com.mainproject.grilledshrimp.domain.user.dto.*;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.mapper.UserMapper;
import com.mainproject.grilledshrimp.domain.user.service.AuthUserDetailsService;
import com.mainproject.grilledshrimp.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    // 유저 생성
    @PostMapping("/signup")
    public ResponseEntity postUser(@Valid @RequestBody UserPostDto userPostDto) {
        Users users = userService.createUser(mapper.userPostDtoToUser(userPostDto));
        return new ResponseEntity(mapper.userToUserResponseDto(users), HttpStatus.CREATED);
        //return new ResponseEntity(mapper.userToUserResponseSimpleDto(users), HttpStatus.CREATED);
    }

    // 유저 사진 등록
    @PostMapping("/{user-id}/image")
    public ResponseEntity postUserImage(
            @PathVariable("user-id") long userId,
            @RequestParam(value = "file")MultipartFile file) {
        Users user = userService.uploadImage(userId, file);
        UserResponseDto responseDto = mapper.userToUserResponseDto(user);

        return new ResponseEntity<>((responseDto),HttpStatus.OK);
    }

    // 유저 로그아웃
    @PostMapping("/logout")
    public ResponseEntity logoutUser(@Valid @RequestBody UserLogoutDto userLogoutDto) {
        userService.logoutUser(userLogoutDto.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }
//    // 유저 사진 수정
//    @PatchMapping("/{user-id}/image")
//    public ResponseEntity patchUserImage(@Valid @RequestBody UserProfileImageDto userProfileImageDto) {
//
//        return new ResponseEntity(HttpStatus.OK);
//    }

    // 유저 사진 삭제도 있어야 할 듯


    // 유저 정보 수정
    // 유저 정보 수정DTO에 수정할 정보 여러개 넣고 필요한 값만 받아 바꾸면 됨
    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(@Valid @RequestBody UserPatchDto userPatchDto, @PathVariable("user-id") Long userId) {
        Users updateUser = userService.updateUser(userId, userPatchDto);
        return new ResponseEntity(mapper.userToUserResponseDto(updateUser), HttpStatus.OK);
    }

    // 특정 유저의 전체 게시글 가져오기
    @GetMapping("/posts")
    public ResponseEntity getUserPosts() {

        return new ResponseEntity(HttpStatus.OK);
    }

    // 특정 유저의 정보 가져오기
    @GetMapping("/{user-id}")
    public ResponseEntity getUserInfo(@PathVariable("user-id") long userId) {
        Users findUser = userService.findUser(userId);
        return new ResponseEntity<>(mapper.userToUserResponseDto(findUser), HttpStatus.OK);
    }

    // 모든 유저 정보 가져오기
    @GetMapping
    public ResponseEntity getAllUserInfo() {
        List<Users> users = userService.findAllUser();
        return new ResponseEntity(mapper.usersToUserResponseDtos(users), HttpStatus.OK);
    }

    // 특정 유저 삭제
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
