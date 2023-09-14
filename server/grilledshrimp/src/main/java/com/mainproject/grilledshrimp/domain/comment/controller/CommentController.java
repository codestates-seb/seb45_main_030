package com.mainproject.grilledshrimp.domain.comment.controller;

import com.mainproject.grilledshrimp.domain.comment.service.CommentService;
import com.mainproject.grilledshrimp.domain.post.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments") // 기본 URL 경로를 "/comments"로 설정
public class CommentController {
    @Autowired
    private PostsService postService;
    @Autowired
    private CommentService commentService;
    @PostMapping("/posts/{postId}")
    @ResponseBody // JSON 응답을 반환하기 위해 @ResponseBody 어노테이션 사용
    public String addComment(
            @RequestParam("userId") Long userId,
            @RequestParam("postId") Long postId,
            @RequestParam("commentText") String commentText) {
        commentService.addComment(userId,postId, commentText);
        return "redirect:/post/" + postId;
    }

}
