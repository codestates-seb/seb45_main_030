package com.mainproject.grilledshrimp.domain.post.service;

import com.mainproject.grilledshrimp.domain.post.dto.PostsPostDto;
import com.mainproject.grilledshrimp.domain.post.dto.PostsResponseDto;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
//import com.mainproject.grilledshrimp.domain.postTag.entity.PostTag;
import com.mainproject.grilledshrimp.domain.postTag.entity.PostTag;
import com.mainproject.grilledshrimp.domain.tag.entity.Tag;
import com.mainproject.grilledshrimp.domain.tag.repository.TagRepository;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import com.mainproject.grilledshrimp.global.image.AwsS3Service;
import lombok.RequiredArgsConstructor;
import com.mainproject.grilledshrimp.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AwsS3Service awsS3Service;

    @Autowired
    private TagRepository tagRepository;

    public Posts createPost(PostsPostDto postsPostDto) {
        Users user = userRepository.findByUserId(postsPostDto.getUserId()).orElseThrow(
                ()-> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Posts post = postsPostDto.toPosts(user);
        addPostTagsToPost(postsPostDto.getTags(), post);
        return postsRepository.save(post);

    }


    public Posts updatePost(Posts post){
        Posts findPosts = postsRepository.findById(post.getPostId()).orElseThrow(
                ()-> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)
        );
        postsRepository.save(post);
        return post;
    }

    public String uploadImage(MultipartFile file){
        String fileName = awsS3Service.uploadImage(file);
        return fileName;
    }

    public PostsResponseDto findPost(long postId){
        Posts post = postsRepository.findById(postId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        return PostsResponseDto.of(post);
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> findPosts(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Posts> pagePosts = postsRepository.findPosts(pageable);
        return pagePosts.map(PostsResponseDto::of);
    }

    public void deletePost(long postId, Long userId){
        Posts findPosts = postsRepository.findById(postId).orElseThrow();
        if(Objects.equals(findPosts.getUsers().getUserId(), userId)){
            postsRepository.deleteById(postId);
        }
    }

    public void addPostTagsToPost(List<String> tagNames, Posts post) {
        List<PostTag> postTags = tagNames.stream()
                .map(tagName -> {
                    Tag tag = tagRepository.findTagByName(tagName)
                            .orElseGet(() -> tagRepository.save(Tag.builder()
                                    .name(tagName)
                                    .build()));
                    return new PostTag(post, tag);
                        }).collect(Collectors.toList());

        post.getPostTags().addAll(postTags);


    }
}
