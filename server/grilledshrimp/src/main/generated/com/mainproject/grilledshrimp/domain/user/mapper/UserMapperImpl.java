package com.mainproject.grilledshrimp.domain.user.mapper;

import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.recommendComment.entity.RecommendComment;
import com.mainproject.grilledshrimp.domain.recommendPost.entity.RecommendPost;
import com.mainproject.grilledshrimp.domain.user.dto.UserPatchDto;
import com.mainproject.grilledshrimp.domain.user.dto.UserPostDto;
import com.mainproject.grilledshrimp.domain.user.dto.UserResponseDto;
import com.mainproject.grilledshrimp.domain.user.dto.UserResponseSimpleDto;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-14T13:20:55+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public Users userPostDtoToUser(UserPostDto userPostDto) {
        if ( userPostDto == null ) {
            return null;
        }

        Users users = new Users();

        users.setUsername( userPostDto.getUsername() );
        users.setPassword( userPostDto.getPassword() );
        users.setEmail( userPostDto.getEmail() );

        return users;
    }

    @Override
    public Users userPatchDtoToUser(UserPatchDto userPatchDto) {
        if ( userPatchDto == null ) {
            return null;
        }

        Users users = new Users();

        users.setUsername( userPatchDto.getUsername() );
        users.setPassword( userPatchDto.getPassword() );

        return users;
    }

    @Override
    public UserResponseDto userToUserResponseDto(Users users) {
        if ( users == null ) {
            return null;
        }

        Long userId = null;
        String username = null;
        String email = null;
        String introduction = null;
        Users.UserStatus userStatus = null;
        String profileImage = null;
        LocalDateTime createdAt = null;
        LocalDateTime modifiedAt = null;
        List<String> role = null;
        List<Bookmark> bookmarks = null;
        List<RecommendPost> recommendPostList = null;
        List<RecommendComment> recommendCommentList = null;

        userId = users.getUserId();
        username = users.getUsername();
        email = users.getEmail();
        introduction = users.getIntroduction();
        userStatus = users.getUserStatus();
        profileImage = users.getProfileImage();
        createdAt = users.getCreatedAt();
        modifiedAt = users.getModifiedAt();
        List<String> list = users.getRole();
        if ( list != null ) {
            role = new ArrayList<String>( list );
        }
        List<Bookmark> list1 = users.getBookmarks();
        if ( list1 != null ) {
            bookmarks = new ArrayList<Bookmark>( list1 );
        }
        List<RecommendPost> list2 = users.getRecommendPostList();
        if ( list2 != null ) {
            recommendPostList = new ArrayList<RecommendPost>( list2 );
        }
        List<RecommendComment> list3 = users.getRecommendCommentList();
        if ( list3 != null ) {
            recommendCommentList = new ArrayList<RecommendComment>( list3 );
        }

        UserResponseDto userResponseDto = new UserResponseDto( userId, username, email, introduction, userStatus, profileImage, createdAt, modifiedAt, role, bookmarks, recommendPostList, recommendCommentList );

        return userResponseDto;
    }

    @Override
    public UserResponseSimpleDto userToUserResponseSimpleDto(Users users) {
        if ( users == null ) {
            return null;
        }

        UserResponseSimpleDto userResponseSimpleDto = new UserResponseSimpleDto();

        userResponseSimpleDto.setUserId( users.getUserId() );
        userResponseSimpleDto.setUsername( users.getUsername() );
        userResponseSimpleDto.setEmail( users.getEmail() );
        userResponseSimpleDto.setProfileImage( users.getProfileImage() );

        return userResponseSimpleDto;
    }

    @Override
    public List<UserResponseDto> usersToUserResponseDtos(List<Users> users) {
        if ( users == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( users.size() );
        for ( Users users1 : users ) {
            list.add( userToUserResponseDto( users1 ) );
        }

        return list;
    }
}
