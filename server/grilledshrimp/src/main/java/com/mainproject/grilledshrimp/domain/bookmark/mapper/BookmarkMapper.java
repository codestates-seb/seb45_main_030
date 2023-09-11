package com.mainproject.grilledshrimp.domain.bookmark.mapper;

import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkPostDto;
import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkResponseDto;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {
    Bookmark bookmarkPostDtoToBookmark(BookmarkPostDto bookmarkPostDto);
    BookmarkResponseDto bookmarkToBookmarkResponseDto(Bookmark bookmark);
    List<BookmarkResponseDto> bookmarkListToBookmarkResponseDtoList(List<Bookmark> bookmarkList);
}
