package com.mainproject.grilledshrimp.domain.bookmark.service;

import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkPostDto;
import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
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
    private final PostsRepository postsRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository usersRepository, PostsRepository postsRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.usersRepository = usersRepository;
        this.postsRepository = postsRepository;
    }


    // 북마크 생성
    public Bookmark createBookmark(BookmarkPostDto bookmarkPostDto) {
        Optional<Users> findUser = usersRepository.findById(bookmarkPostDto.getUser_id());
        Optional<Posts> findPost = postsRepository.findById(bookmarkPostDto.getPost_id());

        Users user = findUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Posts post = findPost.orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));

        existsBookmark(bookmarkPostDto);

        Bookmark bookmark = bookmarkPostDto.dtoToEntity(user, post);
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        log.info("북마크 생성");
        return savedBookmark;
    }

    // 북마크 조회
    public List<Bookmark> findBookmark(long userId) {

        return findVerifiedBookmark(userId);
    }

    // 특정 유저 북마크 전체 삭제
    public void deleteBookmark(long userId) {

        bookmarkRepository.deleteById(userId);
    }

    // 특정 유저 특정 북마크 삭제
    public void deleteBookmark(long userId, long postId) {
        Optional<Bookmark> findBookmark = bookmarkRepository.findByUsers_UserIdAndPosts_PostId(userId, postId);
        if(findBookmark.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND);
        }
        bookmarkRepository.deleteById(findBookmark.get().getBookmarkId());
    }

    // 특정 북마크가 존재하는지 확인합니다.
    private List<Bookmark> findVerifiedBookmark(long userId) {
        Optional<List<Bookmark>> findBookmark = bookmarkRepository.findByUsers_UserId(userId);
        if(findBookmark.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND);
        }
        return findBookmark.get();
    }

    // 이미 존재하는 북마크인지 확인합니다.
    private void existsBookmark(BookmarkPostDto bookmarkPostDto) {
        Optional<Bookmark> findBookmark = bookmarkRepository.findByUsers_UserIdAndPosts_PostId(bookmarkPostDto.getUser_id(), bookmarkPostDto.getPost_id());
        if(findBookmark.isPresent() && findBookmark.get().getBookmarkName().equals(bookmarkPostDto.getBookmarkName())) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_EXIST);
        }
    }
}
