package com.mainproject.grilledshrimp.domain.post.entity;

import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.postTag.entity.PostTag;
import com.mainproject.grilledshrimp.domain.recommendPost.entity.RecommendPost;
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
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = true)
    private String postCaption;

    @Column(nullable = false)
    private String postImage;

    @Column(nullable = true)
    private String postAddress;

    @Column(nullable = false)
    private boolean postCommentPermission;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "posts", orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "posts", orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @OneToMany(mappedBy = "posts", orphanRemoval = true)
    private List<RecommendPost> recommendPostList = new ArrayList<>();
}
