package com.mainproject.grilledshrimp.domain.comment.dto;

import lombok.Getter;

// 댓글 생성
@Getter
public class CommentPostDto {
    private Long postId;
    private Long userId;
    private String commentText;
}