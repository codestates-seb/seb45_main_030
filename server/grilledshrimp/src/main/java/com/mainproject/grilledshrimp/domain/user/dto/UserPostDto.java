package com.mainproject.grilledshrimp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPostDto {
    private String username;
    private String email;
    private String password;
}
