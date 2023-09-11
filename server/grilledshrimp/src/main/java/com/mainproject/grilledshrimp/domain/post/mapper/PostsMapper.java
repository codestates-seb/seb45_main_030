package com.mainproject.grilledshrimp.domain.post.mapper;

import com.mainproject.grilledshrimp.domain.post.dto.PostsPostDto;
import com.mainproject.grilledshrimp.domain.post.dto.PostsResponseDto;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostsMapper {
    Posts postsPostDtoToPosts(PostsPostDto postsPostDto);

    PostsResponseDto postsToPostsResponseDto(Posts posts);
    List<PostsResponseDto> postsToPostsResponseDtos(List<Posts> posts);
}
