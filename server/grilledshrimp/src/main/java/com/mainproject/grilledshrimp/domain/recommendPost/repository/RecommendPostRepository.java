package com.mainproject.grilledshrimp.domain.recommendPost.repository;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.recommendPost.entity.RecommendPost;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendPostRepository extends JpaRepository<RecommendPost, Long>{
    Optional<RecommendPost> findByUsersAndPosts(Users users, Posts posts);
}
