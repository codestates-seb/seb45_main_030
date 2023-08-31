package com.mainproject.grilledshrimp.domain.recommendComment.entity;

import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class RecommendComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommend_comment_id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
