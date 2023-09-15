package com.mainproject.grilledshrimp.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import com.mainproject.grilledshrimp.domain.postTag.entity.PostTag;
import com.mainproject.grilledshrimp.domain.recommend.entity.Recommend;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "post_image", nullable = false)
    private String postImage;

    @Column(name = "post_address", nullable = true)
    private String postAddress;

    @Column(name = "post_comment_permission", nullable = false)
    private boolean postCommentPermission;

    @Column(name = "thumbnail", nullable = false)
    public String thumbnail;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "posts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    private List<PostTag> postTags;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    private List<Recommend> recommendList = new ArrayList<>();

    @OneToMany(mappedBy = "posts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

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

    public List<Comment> getComments() {
        if(comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }


}
