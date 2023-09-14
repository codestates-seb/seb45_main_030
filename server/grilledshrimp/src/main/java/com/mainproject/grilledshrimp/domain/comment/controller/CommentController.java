package com.mainproject.grilledshrimp.domain.comment.controller;

import com.mainproject.grilledshrimp.domain.comment.dto.CommentDTO;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentPatchDto;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentPostDto;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.comment.service.CommentService;
import com.mainproject.grilledshrimp.domain.post.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments") // 기본 URL 경로를 "/comments"로 설정
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/posts/{postId}")
    @ResponseBody // JSON 응답을 반환하기 위해 @ResponseBody 어노테이션 사용
    public String addComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentPostDto commentPostDTO) {
        commentService.addComment(commentPostDTO.getUserId(),
                postId,
                commentPostDTO.getCommentText());
        return "redirect:/post/" + postId;
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentPatchDto commentUpdateDTO) {
        // 댓글 수정 로직 구현
        commentService.updateComment(commentId, commentUpdateDTO.getUpdatedText());

        return ResponseEntity.ok("수정완료");
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        // 댓글 삭제 로직 구현
        commentService.deleteComment(commentId);

        return ResponseEntity.ok("삭제완료");
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Page<CommentDTO>> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Page<CommentDTO> comments = commentService.getCommentsByPostId(postId, PageRequest.of(page, size));

        return ResponseEntity.ok(comments);
    }
}
