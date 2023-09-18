package com.mainproject.grilledshrimp.domain.post.dto;


import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PostsResponseSimpleDto {
    private Long postId;
    private String thumbnail;

    static public PostsResponseDto of(Posts posts){
        PostsResponseDto postsResponseDto = new PostsResponseDto();
        postsResponseDto.setPostId(posts.getPostId());
        postsResponseDto.setThumbnail(posts.getThumbnail());
        return postsResponseDto;
    }
}
