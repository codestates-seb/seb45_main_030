package com.mainproject.grilledshrimp.domain.bookmark.mapper;

import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkPostDto;
import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkResponseDto;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {
    default BookmarkResponseDto bookmarkToBookmarkResponseDto(Bookmark bookmark) {
        return new BookmarkResponseDto(
                bookmark.getUsers().getUserId(),
                bookmark.getPosts().getPostId(),
                bookmark.getBookmarkName(),
                bookmark.getBookmarkId()
        );
    }
    List<BookmarkResponseDto> bookmarkListToBookmarkResponseDtoList(List<Bookmark> bookmarkList);
}
