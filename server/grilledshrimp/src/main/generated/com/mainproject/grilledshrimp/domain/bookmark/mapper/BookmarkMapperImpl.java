package com.mainproject.grilledshrimp.domain.bookmark.mapper;

import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkResponseDto;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
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
public class BookmarkMapperImpl implements BookmarkMapper {

    @Override
    public List<BookmarkResponseDto> bookmarkListToBookmarkResponseDtoList(List<Bookmark> bookmarkList) {
        if ( bookmarkList == null ) {
            return null;
        }

        List<BookmarkResponseDto> list = new ArrayList<BookmarkResponseDto>( bookmarkList.size() );
        for ( Bookmark bookmark : bookmarkList ) {
            list.add( bookmarkToBookmarkResponseDto( bookmark ) );
        }

        return list;
    }
}
