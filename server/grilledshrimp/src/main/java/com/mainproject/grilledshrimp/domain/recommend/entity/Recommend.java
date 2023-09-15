package com.mainproject.grilledshrimp.domain.recommend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommend_post_id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Posts posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users users;
}
