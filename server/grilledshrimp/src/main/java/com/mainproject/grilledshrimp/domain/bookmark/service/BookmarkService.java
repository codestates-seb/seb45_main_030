package com.mainproject.grilledshrimp.domain.bookmark.service;

import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import com.mainproject.grilledshrimp.domain.bookmark.repository.BookmarkRepository;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    // 북마크 생성
    public Bookmark createBookmark(Bookmark bookmark) {
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        log.info("북마크 생성");
        return savedBookmark;
    }

    // 북마크 조회
    public List<Bookmark> findBookmark(long userId) {

        return findVerifiedBookmark(userId);
    }

    // 북마크 삭제
    public void deleteBookmark(long userId) {

        bookmarkRepository.deleteById(userId);
    }

    // 특정 북마크가 존재하는지 확인합니다.
    private List<Bookmark> findVerifiedBookmark(long userId) {
        Optional<List<Bookmark>> findBookmark = bookmarkRepository.findByUsers_UserId(userId);
        List<Bookmark> bookmarkList = findBookmark.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND));
        return bookmarkList;
    }
}
