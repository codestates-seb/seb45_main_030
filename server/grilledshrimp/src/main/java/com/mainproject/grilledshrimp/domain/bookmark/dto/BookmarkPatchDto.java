package com.mainproject.grilledshrimp.domain.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class BookmarkPatchDto {
    @NotNull(message = "유저 아이디를 입력해주세요.")
    private Long user_id;
    private Long post_id;
    private String bookmark_name_old;
    private String bookmark_name_new;
}
