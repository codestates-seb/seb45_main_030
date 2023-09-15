package com.mainproject.grilledshrimp.domain.post.controller;

import com.mainproject.grilledshrimp.domain.post.dto.PostsPostDto;
import com.mainproject.grilledshrimp.domain.post.dto.PostsResponseDto;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.mapper.PostsMapper;
import com.mainproject.grilledshrimp.domain.user.service.UserService;
import com.mainproject.grilledshrimp.global.image.AwsS3Service;
import com.mainproject.grilledshrimp.global.response.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import com.mainproject.grilledshrimp.domain.post.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Validated
@RequiredArgsConstructor
@Slf4j
public class PostsController {
    private final PostsService postsService;
    private final UserService userService;
    private final PostsMapper mapper;
    private final AwsS3Service awsS3Service;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity postPosts(
            @RequestPart("data") PostsPostDto postsPostDto,
            @RequestPart("postImage") MultipartFile file
    ) {
        String imgUrl = awsS3Service.uploadImage(file);
        postsPostDto.setPostImage(imgUrl);

        Posts createdPost = postsService.createPost(postsPostDto);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
//        return new ResponseEntity<>(imgUrl, HttpStatus.CREATED);
    }




    @GetMapping("/{post-id}")
    public ResponseEntity getPost(@PathVariable("post-id")@Positive long postId){
        PostsResponseDto responseDto = postsService.findPost(postId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
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
