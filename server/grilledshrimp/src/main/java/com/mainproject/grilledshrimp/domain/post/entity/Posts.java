package com.mainproject.grilledshrimp.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.post.dto.PostsPostDto;
import com.mainproject.grilledshrimp.domain.postTag.entity.PostTag;
import com.mainproject.grilledshrimp.domain.recommendPost.entity.RecommendPost;
import com.mainproject.grilledshrimp.domain.tag.entity.Tag;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_caption", nullable = true)
    private String postCaption;

    //@Column(name = "post_image", nullable = false)
    private String postImage;

    @Column(name = "post_address", nullable = true)
    private String postAddress;

    @Column(name = "post_comment_permission", nullable = false)
    private boolean postCommentPermission;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference // 양방향 참조 방지
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "posts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    private List<PostTag> postTags;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<RecommendPost> recommendPostList = new ArrayList<>();

    public void addUser(Users user){
        this.users = user;
        if(!user.getPosts().contains(this)){
            user.getPosts().add(this);
        }
    }

    public List<PostTag> getPostTags() {
        if (postTags == null) {
            postTags = new ArrayList<>();
        }
        return postTags;
    }


}
