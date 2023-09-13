package com.mainproject.grilledshrimp.domain.user.dto;

import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 간단한 유저 정보 조회 응답 DTO
@Getter
@Setter
@Data
public class UserResponseSimpleDto {
    private Long userId;
    private String username;
    private String email;
    private String profileImage;

    static public UserResponseSimpleDto of(Users user){
        UserResponseSimpleDto userResponseSimpleDto = new UserResponseSimpleDto();
        userResponseSimpleDto.setUserId(user.getUserId());
        userResponseSimpleDto.setUsername(user.getUsername());
        userResponseSimpleDto.setEmail(user.getEmail());
        userResponseSimpleDto.setProfileImage(user.getProfileImage());

        return userResponseSimpleDto;
    }
}
