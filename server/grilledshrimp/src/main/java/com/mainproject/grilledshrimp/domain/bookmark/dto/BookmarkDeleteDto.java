package com.mainproject.grilledshrimp.domain.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class BookmarkDeleteDto {
    private Long bookmarkId;
    @NotNull(message = "유저 아이디를 입력해주세요.")
    private Long user_id;
    private Long post_id;
    private String bookmark_name;
}
