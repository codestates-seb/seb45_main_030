package com.mainproject.grilledshrimp.domain.post.dto;

import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

// 게시글 클라이언트에게 응답
public class PostResponseDto {
    private Long user_id;

    private Long post_id;
    private String post_title;
    private String post_caption;
    private String post_image;
    private String post_address;
    private boolean post_comment_permission;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime modified_at = LocalDateTime.now();

    // 댓글 리스트
    private List<CommentResponseDto> comments;
}
