package com.mainproject.grilledshrimp.domain.postTag.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.tag.entity.Tag;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostTag {
    @Id
    @GeneratedValue
    @Column(name = "postTag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Posts posts;

    public PostTag(Posts post, Tag tag) {
        this.posts = post;
        this.tag = tag;
    }
}
