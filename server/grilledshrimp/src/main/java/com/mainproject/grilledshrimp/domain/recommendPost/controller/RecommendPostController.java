package com.mainproject.grilledshrimp.domain.recommendPost.controller;

import com.mainproject.grilledshrimp.domain.recommendPost.dto.RecommendPostResponseDto;
import com.mainproject.grilledshrimp.domain.recommendPost.service.RecommendPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendPostController {
    private final RecommendPostService recommendPostService;

    @PostMapping("/{post-id}")
    public ResponseEntity recommendPost(
            @PathVariable("post-id") Long postId,
            @Positive @RequestParam Long userId) {
        RecommendPostResponseDto responseDto
                = recommendPostService.recommendPost(postId, userId);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}
