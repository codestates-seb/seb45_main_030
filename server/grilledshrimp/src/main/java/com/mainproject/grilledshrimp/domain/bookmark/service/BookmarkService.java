package com.mainproject.grilledshrimp.domain.bookmark.service;

import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkPostDto;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.post.entity.Post;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
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
    private final UserRepository usersRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository usersRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.usersRepository = usersRepository;
    }


    // 북마크 생성
    public Bookmark createBookmark(BookmarkPostDto bookmarkPostDto) {
        Optional<Users> findUser = usersRepository.findById(bookmarkPostDto.getUser_id());
        //Optional<Post> findPost = usersRepository.findById(bookmarkPostDto.getPost_id());
        Users user = findUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Post post = new Post();

        Bookmark bookmark = bookmarkPostDto.dtoToEntity(user, post);
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
        if(findBookmark.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND);
        }
        List<Bookmark> bookmarkList = findBookmark.get();
        return bookmarkList;
    }
}
