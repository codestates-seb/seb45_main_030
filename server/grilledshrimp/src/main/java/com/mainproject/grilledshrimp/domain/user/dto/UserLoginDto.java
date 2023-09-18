package com.mainproject.grilledshrimp.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserLoginDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
