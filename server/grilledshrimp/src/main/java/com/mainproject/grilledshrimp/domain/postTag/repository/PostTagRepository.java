package com.mainproject.grilledshrimp.domain.postTag.repository;

import com.mainproject.grilledshrimp.domain.postTag.entity.PostTag;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    Optional<List<PostTag>> findByPosts_PostId(Long postId);
    Optional<List<PostTag>> findByTag_Id(Long tagId);
}
