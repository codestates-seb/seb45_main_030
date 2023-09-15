package com.mainproject.grilledshrimp.domain.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkDeleteDto {
    private Long user_id;
    private Long post_id;
}
