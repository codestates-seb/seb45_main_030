package com.mainproject.grilledshrimp.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class UserCheckDto {
    @Email(message = "이메일 형식을 지켜주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;
}
