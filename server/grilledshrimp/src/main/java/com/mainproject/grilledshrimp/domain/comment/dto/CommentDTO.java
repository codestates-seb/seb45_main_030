package com.mainproject.grilledshrimp.domain.comment.dto;

import com.mainproject.grilledshrimp.domain.user.entity.Users;

public class CommentDTO {
    private Long userId;
    private String commentText;
    private Users commentId;

    // 추가 필드
    private int totalPages;
    private long totalElements;
    private int currentPage;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setCommentId(Users commentId) {
        this.commentId = commentId;
    }
}