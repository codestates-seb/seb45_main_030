package com.mainproject.grilledshrimp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 간단한 유저 정보 조회 응답 DTO
@Getter
@Setter
@AllArgsConstructor
public class UserResponseSimpleDto {
    private Long userId;
    private String username;
    private String email;
    private String profileImage;
}
