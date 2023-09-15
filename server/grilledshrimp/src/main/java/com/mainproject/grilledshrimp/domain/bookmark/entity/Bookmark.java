package com.mainproject.grilledshrimp.domain.bookmark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.*;


import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;
    
    // 외래키
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users users;
    
    // 외래키
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference
    private Posts posts;

    @Column(nullable = false)
    private String bookmarkName;
}
