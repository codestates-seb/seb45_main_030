package com.mainproject.grilledshrimp.domain.comment.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}