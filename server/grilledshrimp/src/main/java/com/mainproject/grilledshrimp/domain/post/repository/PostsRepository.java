package com.mainproject.grilledshrimp.domain.post.repository;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {

}
