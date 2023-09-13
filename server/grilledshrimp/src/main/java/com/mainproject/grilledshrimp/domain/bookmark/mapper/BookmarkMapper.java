package com.mainproject.grilledshrimp.domain.bookmark.mapper;

import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkPostDto;
import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkResponseDto;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.post.entity.Post;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {
    default Bookmark bookmarkPostDtoToBookmark(BookmarkPostDto bookmarkPostDto) {
        Bookmark bookmark = new Bookmark();
        Users user = new Users();
        Post post = new Post();
        user.setUserId(bookmarkPostDto.getUser_id());
        post.setPost_id(bookmarkPostDto.getPost_id());

        bookmark.setUsers(user);
        bookmark.setPost(post);
        bookmark.setBookmarkName(bookmarkPostDto.getBookmarkName());
        return bookmark;
    }

    default BookmarkResponseDto bookmarkToBookmarkResponseDto(Bookmark bookmark) {
        return new BookmarkResponseDto(
                bookmark.getUsers().getUserId(),
                bookmark.getPost().getPost_id(),
                bookmark.getBookmarkName(),
                bookmark.getBookmarkId()
        );
    }
    List<BookmarkResponseDto> bookmarkListToBookmarkResponseDtoList(List<Bookmark> bookmarkList);
}
