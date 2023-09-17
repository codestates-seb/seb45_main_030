package com.mainproject.grilledshrimp.domain.comment.dto;

import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import com.mainproject.grilledshrimp.domain.user.dto.UserResponseSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 댓글 조회
@Data
@Getter
@Setter
public class CommentResponseDto {
    private Long postId;
    private UserResponseSimpleDto user;
    private Long commentId;
    private String commentText;
    public static CommentResponseDto of(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setPostId(comment.getPosts().getPostId());
        commentResponseDto.setUser(UserResponseSimpleDto.of(comment.getUsers()));
        commentResponseDto.setCommentId(comment.getCommentId());
        commentResponseDto.setCommentText(comment.getCommentText());
        return commentResponseDto;
    }
}
