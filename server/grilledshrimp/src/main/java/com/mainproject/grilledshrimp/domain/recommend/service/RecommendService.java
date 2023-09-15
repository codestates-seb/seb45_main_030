package com.mainproject.grilledshrimp.domain.recommend.service;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
import com.mainproject.grilledshrimp.domain.recommend.dto.RecommendResponseDto;
import com.mainproject.grilledshrimp.domain.recommend.entity.Recommend;
import com.mainproject.grilledshrimp.domain.recommend.repository.RecommendRepository;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    public RecommendResponseDto recommendPost(Long postId, Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));

        Optional<Recommend> recommendPosts = recommendRepository.findByUsersAndPosts(user, post);
        if (recommendPosts.isEmpty()){
            Recommend recommend = Recommend.builder()
                    .posts(post)
                    .users(user)
                    .build();
            recommendRepository.save(recommend);

            return RecommendResponseDto.builder()
                    .post_id(postId)
                    .user_id(userId)
                    .build();
        }

        Recommend recommend = recommendPosts.get();
        recommendRepository.delete(recommend);
        return null;
    }

}
