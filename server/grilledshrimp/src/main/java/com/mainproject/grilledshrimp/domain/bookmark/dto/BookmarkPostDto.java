package com.mainproject.grilledshrimp.domain.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.post.entity.Post;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 북마크 생성
@AllArgsConstructor
@Getter
public class BookmarkPostDto {
    private Long user_id;

    private Long post_id;

    @JsonProperty("bookmark_name")
    private String bookmarkName;

    public Bookmark dtoToEntity(Users user, Post post) {
        return Bookmark.builder()
                .users(user)
                .post(post)
                .bookmarkName(bookmarkName)
                .build();
    }

}
