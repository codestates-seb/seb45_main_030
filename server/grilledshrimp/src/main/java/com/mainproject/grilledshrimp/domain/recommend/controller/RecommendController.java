package com.mainproject.grilledshrimp.domain.recommend.controller;

import com.mainproject.grilledshrimp.domain.post.dto.PostsResponseSimpleDto;
import com.mainproject.grilledshrimp.domain.recommend.dto.RecommendResponseDto;
import com.mainproject.grilledshrimp.domain.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;

    @PostMapping("/{post-id}")
    public ResponseEntity recommendPost(
            @PathVariable("post-id") Long postId,
            @Positive @RequestParam Long userId) {
        RecommendResponseDto responseDto
                = recommendService.recommendPost(postId, userId);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity recommendGet(
            @PathVariable("user-id") Long userId){
        List<PostsResponseSimpleDto> responseDto = recommendService.findRecommendPosts(userId);

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}
