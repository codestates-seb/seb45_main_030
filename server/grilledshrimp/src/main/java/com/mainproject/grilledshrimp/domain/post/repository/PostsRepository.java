package com.mainproject.grilledshrimp.domain.post.repository;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findAll(Pageable pageable);
    @Query("SELECT p FROM Posts p ORDER BY p.postId DESC")
    Page<Posts> findPosts(Pageable pageable);
}
