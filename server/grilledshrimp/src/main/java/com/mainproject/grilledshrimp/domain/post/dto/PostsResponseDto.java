package com.mainproject.grilledshrimp.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.tag.entity.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// 게시글 클라이언트에게 응답
@Data
public class PostsResponseDto {
    private Long postId;
    private String postTitle;
    private String postCaption;
    private String postImage;
    private String postAddress;
    private boolean postCommentPermission;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<String> tags;
//    private List<Tag> tags;
    // 댓글 리스트
    private List<CommentResponseDto> comments;

    static public PostsResponseDto of(Posts post){
        PostsResponseDto postsResponseDto = new PostsResponseDto();
        postsResponseDto.setPostId(post.getPostId());
        postsResponseDto.setPostTitle(post.getPostTitle());
        postsResponseDto.setPostCaption(post.getPostCaption());
        postsResponseDto.setPostImage(post.getPostImage());
        postsResponseDto.setPostAddress(post.getPostAddress());
        postsResponseDto.setPostCommentPermission(post.isPostCommentPermission());
        postsResponseDto.setCreatedAt(post.getCreatedAt());
        postsResponseDto.setModifiedAt(post.getModifiedAt());
        postsResponseDto.setTags(post.getPostTags().stream()
                .map(postTag -> postTag.getTag().getName())
                .collect(Collectors.toList()));
        return postsResponseDto;
    }
}
