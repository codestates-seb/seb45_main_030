package com.mainproject.grilledshrimp.domain.recommend.dto;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.recommend.entity.Recommend;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

// 게시글 추천 수정
@Builder
@Data
@Getter
@AllArgsConstructor
public class RecommendPostDto {
    private Long post_id;
    private Long user_id;

}
