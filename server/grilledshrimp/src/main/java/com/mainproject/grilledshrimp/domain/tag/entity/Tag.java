package com.mainproject.grilledshrimp.domain.tag.entity;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(nullable = false)
    private String name;
}
