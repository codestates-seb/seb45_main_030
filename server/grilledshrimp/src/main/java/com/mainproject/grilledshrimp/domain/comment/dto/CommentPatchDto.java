package com.mainproject.grilledshrimp.domain.comment.dto;

// 댓글 수정
public class CommentPatchDto {
    private Long post_id;
    private Long user_id;
    private String comment_text;

    public String getUpdatedText() {
        return comment_text;
    }
    public void setUpdatedText(String updatedText) {
        this.comment_text = updatedText;
    }
}
