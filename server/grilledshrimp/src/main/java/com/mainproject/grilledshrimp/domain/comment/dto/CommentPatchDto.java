package com.mainproject.grilledshrimp.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

// 댓글 수정
@Getter
@Setter
public class CommentPatchDto {
    private Long postId;
    private Long userId;
    private String commentText;

}