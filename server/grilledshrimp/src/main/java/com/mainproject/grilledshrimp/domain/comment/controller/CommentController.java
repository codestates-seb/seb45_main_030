package com.mainproject.grilledshrimp.domain.comment.controller;

import com.mainproject.grilledshrimp.domain.comment.dto.CommentRequestDto;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import com.mainproject.grilledshrimp.domain.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성
    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto createdComment = commentService.createComment(postId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }
    // 댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<Comment> patchComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto) {
        Comment updatedComment = commentService.patchComment(commentId, requestDto);
        return ResponseEntity.ok(updatedComment);
    }
    // 특정 게시물에 대한 댓글 목록 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<Object> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
    //삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}