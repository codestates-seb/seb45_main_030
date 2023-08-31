package com.mainproject.grilledshrimp.domain.recommendPost.entity;

import com.mainproject.grilledshrimp.domain.post.entity.Post;
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
public class RecommendPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommend_post_id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
