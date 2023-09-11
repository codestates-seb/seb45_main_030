package com.mainproject.grilledshrimp.domain.post.controller;

import com.mainproject.grilledshrimp.domain.post.dto.PostsPostDto;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.mapper.PostsMapper;
import com.mainproject.grilledshrimp.domain.user.service.UserService;
import com.mainproject.grilledshrimp.global.response.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import com.mainproject.grilledshrimp.domain.post.service.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Validated
@RequiredArgsConstructor
public class PostsController {
    private final PostsService postsService;
    private final UserService userService;
    private final PostsMapper mapper;

    @PostMapping
    public ResponseEntity postPosts(@Valid @RequestBody PostsPostDto postsPostDto) {
        Posts posts = postsService.createPost(mapper.postsPostDtoToPosts(postsPostDto));
        URI location = URI.create("/posts/" + posts.getPostId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{post-id}")
    public ResponseEntity getPost(@PathVariable("post-id")@Positive long postId){
        Posts posts = postsService.findPost(postId);

        return new ResponseEntity<>(mapper.postsToPostsResponseDto(posts), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getPosts(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size){
        Page<Posts> pagePosts = postsService.findPosts(page, size);
        List<Posts> posts = pagePosts.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.postsToPostsResponseDtos(posts), pagePosts), HttpStatus.OK);
    }

}
