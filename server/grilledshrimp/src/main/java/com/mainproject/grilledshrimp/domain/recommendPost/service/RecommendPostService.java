package com.mainproject.grilledshrimp.domain.recommendPost.service;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
import com.mainproject.grilledshrimp.domain.recommendPost.dto.RecommendPostResponseDto;
import com.mainproject.grilledshrimp.domain.recommendPost.entity.RecommendPost;
import com.mainproject.grilledshrimp.domain.recommendPost.repository.RecommendPostRepository;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendPostService {
    private final RecommendPostRepository recommendPostRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    public RecommendPostResponseDto recommendPost(Long postId, Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));

        Optional<RecommendPost> recommendPosts = recommendPostRepository.findByUsersAndPosts(user, post);
        if (recommendPosts.isEmpty()){
            RecommendPost recommendPost = RecommendPost.builder()
                    .posts(post)
                    .users(user)
                    .build();
            recommendPostRepository.save(recommendPost);

            return RecommendPostResponseDto.builder()
                    .post_id(postId)
                    .user_id(userId)
                    .build();
        }

        RecommendPost recommendPost = recommendPosts.get();
        recommendPostRepository.delete(recommendPost);
        return null;
    }

}
