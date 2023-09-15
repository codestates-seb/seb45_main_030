package com.mainproject.grilledshrimp.domain.user.dto;

import com.mainproject.grilledshrimp.domain.recommend.entity.Recommend;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
//    private List<Bookmark> bookmarks;
//    private List<RecommendPost> recommendPostList;
//    private List<RecommendComment> recommendCommentList;

}
