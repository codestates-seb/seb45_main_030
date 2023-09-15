package com.mainproject.grilledshrimp.domain.recommendPost.dto;

import lombok.Builder;
import lombok.Data;

// 게시글 추천 조회
@Data
@Builder
public class RecommendPostResponseDto {
    private Long post_id;
    private Long user_id;
}
