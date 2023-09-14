package com.mainproject.grilledshrimp.domain.comment.dto;

public class CommentRequestDto {
    private String comment_text;
    private Long userId;

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(long l) {
        this.userId = l;
    }
}
