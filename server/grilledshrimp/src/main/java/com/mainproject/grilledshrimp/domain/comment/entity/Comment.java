package com.mainproject.grilledshrimp.domain.comment.entity;

import com.mainproject.grilledshrimp.domain.comment.repository.CommentRepository;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.recommendComment.entity.RecommendComment;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts posts;

    @Column(nullable = false)
    private String comment_text;

    @Column(nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modified_at = LocalDateTime.now();

    @OneToMany(mappedBy = "comment")
    private List<RecommendComment> recommendCommentList = new ArrayList<>();

    // 게시물 ID를 기반으로 댓글을 조회하는 메서드 추가
    public static List<Comment> findByPostId(Long postId) {
        List<Comment> comments = new ArrayList<>();
        comments = CommentRepository.findByPosts_PostId(postId);
        return comments;
    }
}