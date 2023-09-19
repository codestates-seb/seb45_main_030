package com.mainproject.grilledshrimp.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserLogoutDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    String email;
}
