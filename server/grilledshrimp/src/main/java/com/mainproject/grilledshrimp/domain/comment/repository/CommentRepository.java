package com.mainproject.grilledshrimp.domain.comment.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPosts_PostId(Long postId, Pageable pageable);

}