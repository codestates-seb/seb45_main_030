package com.mainproject.grilledshrimp.domain.comment.dto;

import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 댓글 조회
@AllArgsConstructor
@Getter
@Setter
public class CommentResponseDto {
    private Long post_id;
    private Long user_id;
    private Long comment_id;
    private String comment_text;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime modified_at = LocalDateTime.now();


    public CommentResponseDto() {

    }

    public static Comment fromEntity(Comment updatedComment) {
        return updatedComment;
    }
}
