package com.mainproject.grilledshrimp.domain.comment.controller;

import com.mainproject.grilledshrimp.domain.comment.dto.CommentRequestDto;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성 API
    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto createdComment = commentService.createComment(postId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }
}