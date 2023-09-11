package com.mainproject.grilledshrimp.domain.bookmark.entity;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
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
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmark_id;
    
    // 외래키
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    
    // 외래키
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts posts;

    @Column(nullable = false)
    private String bookmark_name;
}
