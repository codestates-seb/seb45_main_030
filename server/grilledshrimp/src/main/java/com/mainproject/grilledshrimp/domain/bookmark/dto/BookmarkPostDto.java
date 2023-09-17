package com.mainproject.grilledshrimp.domain.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

// 북마크 생성
@AllArgsConstructor
@Getter
public class BookmarkPostDto {
    @NotNull(message = "유저 아이디를 입력해주세요.")
    private Long user_id;

    @NotNull(message = "게시글 아이디를 입력해주세요.")
    private Long post_id;

    @JsonProperty("bookmark_name")
    // null값 허용 (북마크 이름이 없을 수도 있음)
    private String bookmarkName;

    public Bookmark dtoToEntity(Users user, Posts post) {
        return Bookmark.builder()
                .users(user)
                .posts(post)
                .build();
    }

}
