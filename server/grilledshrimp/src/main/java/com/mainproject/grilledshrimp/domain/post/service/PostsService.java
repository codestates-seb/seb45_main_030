package com.mainproject.grilledshrimp.domain.post.service;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.mainproject.grilledshrimp.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Posts createPost(Posts post){
        userService.findUser(post.getUser().getUserId());
        return postsRepository.save(post);
    }

    public Posts updatePost(Posts post){
        Posts findPosts = postsRepository.findById(post.getPostId()).orElseThrow();
        postsRepository.save(post);
        return post;
    }

    public Posts findPost(long postId){
        return postsRepository.findById(postId).orElseThrow();
    }

    public Page<Posts> findPosts(int page, int size){
        return postsRepository.findAll(PageRequest.of(page, size, Sort.by("postId").descending()));
    }

    public void deletePost(long postId, Long userId){
        Posts findPosts = postsRepository.findById(postId).orElseThrow();
        if(Objects.equals(findPosts.getUser().getUserId(), userId)){
            postsRepository.deleteById(postId);
        }
    }

}
