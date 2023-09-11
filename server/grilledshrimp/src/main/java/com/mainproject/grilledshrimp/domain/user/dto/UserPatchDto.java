package com.mainproject.grilledshrimp.domain.user.dto;

import lombok.Getter;

@Getter
public class UserPatchDto {
    private String password;
    private String profile_image;
    private String username;
}
