package com.mainproject.grilledshrimp.domain.user.dto;

import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.recommendComment.entity.RecommendComment;
import com.mainproject.grilledshrimp.domain.recommendPost.entity.RecommendPost;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
// 유저 정보 조회 응답 DTO 수정 필요
public class UserResponseDto {
    private Long userId;
    private String username;
    private String email;
    private String introduction;
    private Users.UserStatus userStatus;

    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<String> role;
    private List<Bookmark> bookmarks;
    private List<RecommendPost> recommendPostList;
    private List<RecommendComment> recommendCommentList;
}
