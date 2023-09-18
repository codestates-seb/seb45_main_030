package com.mainproject.grilledshrimp.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserFindEmailByUserNameDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;
}
