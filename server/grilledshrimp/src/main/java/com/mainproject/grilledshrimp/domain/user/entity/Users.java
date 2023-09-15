package com.mainproject.grilledshrimp.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.recommendComment.entity.RecommendComment;
import com.mainproject.grilledshrimp.domain.recommendPost.entity.RecommendPost;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Users {
    // 스네이크 케이스를 사용하면 jpaRepository에서 findByUsername등을 사용할 수 없다.
    // 그래서 카멜 케이스를 사용한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String introduction;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    // 처음 생성할 땐 활동중 상태로 생성한다.
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus = UserStatus.USER_ACTIVE;

    // 처음 생성할 땐 일반 사용자로 생성한다.
    // 별도의 엔티티를 생성하지 않아도 간단하게 매핑 처리가 됨 (String으로 매핑)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role = new ArrayList<>();

    @Column(nullable = true)
    private String profileImage;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "users")
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    @JsonIgnore
    private List<Posts> posts = new ArrayList<>();

    // 북마크 일대다 관계
    @OneToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<RecommendPost> recommendPostList = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    private List<RecommendComment> recommendCommentList = new ArrayList<>();

    public enum UserStatus {
        USER_ACTIVE("활동중"),
        USER_SLEEP("휴면 상태"),
        USER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        UserStatus(String status) {
            this.status = status;
        }
    }

    public enum UserRole {
        ROLE_ADMIN("관리자"),
        ROLE_USER("일반 사용자");

        @Getter
        private String role;

        UserRole(String role) {
            this.role = role;
        }
    }
}
