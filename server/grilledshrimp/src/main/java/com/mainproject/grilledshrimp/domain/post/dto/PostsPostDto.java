package com.mainproject.grilledshrimp.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
//import com.mainproject.grilledshrimp.domain.postTag.entity.PostTag;
import com.mainproject.grilledshrimp.domain.tag.entity.Tag;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

// 게시글 생성
@Data
public class PostsPostDto {
    @JsonProperty("userId")
    private Long userId;

    @NotNull
    @Length(min = 1, max = 100)
    private String postTitle;
    private String postCaption;
    private String postImage;
    private String postAddress;
    private String thumbnail;
    private boolean postCommentPermission;
    @JsonProperty("tags")
    private List<String> tags;

    public Posts toPosts(Users user){
        Posts post = Posts.builder()
                .users(user)
                .postTitle(postTitle)
                .postCaption(postCaption)
                .postImage(postImage)
                .postAddress(postAddress)
                .thumbnail(thumbnail)
                .postCommentPermission(postCommentPermission)
                .build();
        post.addUser(user);
        return post;
    }

}
