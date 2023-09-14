package com.mainproject.grilledshrimp.domain.comment.repository;

import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    static List<Comment> findByPosts_PostId(Long postId) {
        return null;
    }
    List<Comment> findByPost_Id(Long postId);
}
