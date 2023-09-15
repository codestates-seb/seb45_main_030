package com.mainproject.grilledshrimp.domain.post.repository;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByUsers_UserId(Long userId);
    List<Posts> findByPostTitleLike(String title);
}
