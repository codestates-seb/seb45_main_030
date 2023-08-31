package com.mainproject.grilledshrimp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
// 유저 정보 조회 응답 DTO 수정 필요
public class UserResponseDto {
    private String username;
    private String email;
    private String url; // 수정 필요
    private String introduction;
}
