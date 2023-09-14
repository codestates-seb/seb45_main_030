package com.mainproject.grilledshrimp.domain.comment.dto;

// 댓글 생성
public class CommentPostDto {
    private Long post_id;
    private Long user_id;
    private String comment_text;

    public String getCommentText() {
        return comment_text;
    }

    public Long getUserId() {
        return user_id;
    }
}
