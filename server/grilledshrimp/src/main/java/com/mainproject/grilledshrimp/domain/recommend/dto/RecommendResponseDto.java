package com.mainproject.grilledshrimp.domain.recommend.dto;

import lombok.Builder;
import lombok.Data;

// 게시글 추천 조회
@Data
@Builder
public class RecommendResponseDto {
    private Long post_id;
    private Long user_id;
}
