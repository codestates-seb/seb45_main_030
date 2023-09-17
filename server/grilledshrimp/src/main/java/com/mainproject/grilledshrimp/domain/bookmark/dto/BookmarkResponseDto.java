package com.mainproject.grilledshrimp.domain.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 북마크 조회
@AllArgsConstructor
@Getter
public class BookmarkResponseDto {
    private Long user_id;
    private Long post_id;
    private String bookmark_name;

    private Long bookmark_id;
}
