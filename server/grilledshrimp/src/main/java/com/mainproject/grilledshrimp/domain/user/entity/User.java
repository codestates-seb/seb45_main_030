package com.mainproject.grilledshrimp.domain.user.entity;

import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.recommendComment.entity.RecommendComment;
import com.mainproject.grilledshrimp.domain.recommendPost.entity.RecommendPost;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false, unique = true)
    private String user_name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String user_status;

    @Column(nullable = false)
    private String role;

    @Column(nullable = true)
    private String profile_image;

    @Column(nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modified_at;

    // 북마크 일대다 관계
    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RecommendPost> recommendPostList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RecommendComment> recommendCommentList = new ArrayList<>();
}
