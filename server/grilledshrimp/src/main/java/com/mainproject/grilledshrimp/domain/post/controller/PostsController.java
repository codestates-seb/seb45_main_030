package com.mainproject.grilledshrimp.domain.post.controller;

import com.mainproject.grilledshrimp.domain.post.dto.PostsPostDto;
import com.mainproject.grilledshrimp.domain.post.dto.PostsResponseDto;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.mapper.PostsMapper;
import com.mainproject.grilledshrimp.domain.user.service.UserService;
import com.mainproject.grilledshrimp.global.image.AwsS3Service;
import com.mainproject.grilledshrimp.global.exception.response.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import com.mainproject.grilledshrimp.domain.post.service.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@Validated
@RequiredArgsConstructor
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
        String thumbUrl = awsS3Service.uploadThumbnail(file);
        postsPostDto.setPostImage(imgUrl);
        postsPostDto.setThumbnail(thumbUrl);
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
    public ResponseEntity getPosts(@Positive @RequestParam(defaultValue = "1") int page,
                                   @Positive @RequestParam(defaultValue = "30") int size){
        Page<PostsResponseDto> pagePosts = postsService.findPosts(page - 1, size);
        MultiResponseDto<PostsResponseDto> response = new MultiResponseDto<>(pagePosts.getContent(), pagePosts);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{post-id}")
    public ResponseEntity deletePost(@PathVariable("post-id")@Positive long postId,
                           @RequestParam @Positive long userId
    ){
        postsService.deletePost(postId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
