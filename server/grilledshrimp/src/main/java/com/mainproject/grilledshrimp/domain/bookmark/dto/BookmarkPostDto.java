package com.mainproject.grilledshrimp.domain.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 북마크 생성
@AllArgsConstructor
@Getter
public class BookmarkPostDto {
    @JsonProperty("user_id")
    private Long user_id;

    @JsonProperty("post_id")
    private Long post_id;

    @JsonProperty("bookmark_name")
    private String bookmarkName;
}
