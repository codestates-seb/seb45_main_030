package com.mainproject.grilledshrimp.domain.recommend.repository;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.recommend.entity.Recommend;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long>{
    Optional<Recommend> findByUsersAndPosts(Users users, Posts posts);
}
