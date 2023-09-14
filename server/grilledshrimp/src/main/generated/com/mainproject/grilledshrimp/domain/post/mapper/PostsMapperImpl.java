package com.mainproject.grilledshrimp.domain.post.mapper;

import com.mainproject.grilledshrimp.domain.post.dto.PostsPostDto;
import com.mainproject.grilledshrimp.domain.post.dto.PostsResponseDto;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-14T15:02:14+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class PostsMapperImpl implements PostsMapper {

    @Override
    public Posts postsPostDtoToPosts(PostsPostDto postsPostDto) {
        if ( postsPostDto == null ) {
            return null;
        }

        Posts.PostsBuilder posts = Posts.builder();

        posts.postTitle( postsPostDto.getPostTitle() );
        posts.postCaption( postsPostDto.getPostCaption() );
        posts.postImage( postsPostDto.getPostImage() );
        posts.postAddress( postsPostDto.getPostAddress() );
        posts.postCommentPermission( postsPostDto.isPostCommentPermission() );

        return posts.build();
    }

    @Override
    public PostsResponseDto postsToPostsResponseDto(Posts posts) {
        if ( posts == null ) {
            return null;
        }

        PostsResponseDto postsResponseDto = new PostsResponseDto();

        postsResponseDto.setPostId( posts.getPostId() );
        postsResponseDto.setPostTitle( posts.getPostTitle() );
        postsResponseDto.setPostCaption( posts.getPostCaption() );
        postsResponseDto.setPostImage( posts.getPostImage() );
        postsResponseDto.setPostAddress( posts.getPostAddress() );
        postsResponseDto.setPostCommentPermission( posts.isPostCommentPermission() );
        postsResponseDto.setCreatedAt( posts.getCreatedAt() );
        postsResponseDto.setModifiedAt( posts.getModifiedAt() );

        return postsResponseDto;
    }

    @Override
    public List<PostsResponseDto> postsToPostsResponseDtos(List<Posts> posts) {
        if ( posts == null ) {
            return null;
        }

        List<PostsResponseDto> list = new ArrayList<PostsResponseDto>( posts.size() );
        for ( Posts posts1 : posts ) {
            list.add( postsToPostsResponseDto( posts1 ) );
        }

        return list;
    }
}
