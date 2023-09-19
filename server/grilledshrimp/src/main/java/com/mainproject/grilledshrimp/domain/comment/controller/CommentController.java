package com.mainproject.grilledshrimp.domain.comment.controller;

import com.mainproject.grilledshrimp.domain.comment.dto.CommentDTO;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentPatchDto;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentPostDto;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import com.mainproject.grilledshrimp.domain.comment.service.CommentService;
import com.mainproject.grilledshrimp.domain.post.service.PostsService;
import com.mainproject.grilledshrimp.global.exception.response.MultiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments") // 기본 URL 경로를 "/comments"로 설정
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/posts/{postId}")
    public ResponseEntity addComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentPostDto commentPostDTO) {
        CommentResponseDto comment = commentService.addComment(commentPostDTO, postId);

        return new ResponseEntity(comment, HttpStatus.CREATED);
    }
    @PatchMapping("/{commentId}")
    public ResponseEntity updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentPatchDto commentPatchDto) {
        // 댓글 수정 로직 구현
        CommentResponseDto comment = commentService.updateComment(commentId, commentPatchDto);

        return new ResponseEntity(comment, HttpStatus.OK);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        // 댓글 삭제 로직 구현
        commentService.deleteComment(commentId);

        return ResponseEntity.ok("삭제완료");
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Page<CommentResponseDto> comments = commentService.getCommentsByPostId(postId, PageRequest.of(page, size));
        MultiResponseDto<CommentResponseDto> response = new MultiResponseDto<>(comments.getContent(), comments);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}