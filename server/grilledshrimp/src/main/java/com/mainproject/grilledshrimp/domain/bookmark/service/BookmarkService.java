package com.mainproject.grilledshrimp.domain.bookmark.service;

import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkDeleteDto;
import com.mainproject.grilledshrimp.domain.bookmark.dto.BookmarkPatchDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        if(bookmarkPostDto.getBookmarkName() != null) {
            bookmark.setBookmarkName(bookmarkPostDto.getBookmarkName());
        }

        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        log.info("북마크 생성");
        return savedBookmark;
    }

    // 북마크 조회
    public List<Bookmark> findBookmark(long userId) {

        return findVerifiedBookmark(userId);
    }

    // 북마크 수정
    public List<Bookmark> patchBookmark(BookmarkPatchDto bookmarkPatchDto) {
        List<Bookmark> bookmarkList = new ArrayList<>();
        if(bookmarkPatchDto.getPost_id() != null) {
            // 북마크 존재 확인
            List<Bookmark> findBookmarkList = bookmarkRepository.findByPosts_PostId(bookmarkPatchDto.getPost_id())
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND));

            // 북마크 이름 수정
            for(Bookmark bookmark : findBookmarkList) {
                bookmark.setBookmarkName(bookmarkPatchDto.getBookmark_name_new());
                bookmarkList.add(bookmark);
                bookmarkRepository.save(bookmark);
            }
        }
        else {
            // 북마크 존재 확인
            Optional<List<Bookmark>> findBookmark = bookmarkRepository.findByUsers_UserIdAndBookmarkName(bookmarkPatchDto.getUser_id(), bookmarkPatchDto.getBookmark_name_old());
            if(findBookmark.isEmpty()) {
                throw new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND);
            }

            // 북마크 이름 수정
            for(Bookmark bookmark : findBookmark.get()) {
                if(bookmark.getBookmarkName().equals(bookmarkPatchDto.getBookmark_name_old())) {
                    bookmark.setBookmarkName(bookmarkPatchDto.getBookmark_name_new());
                    bookmarkList.add(bookmark);
                    bookmarkRepository.save(bookmark);
                }
            }
        }
        return bookmarkList;
    }

    // 특정 유저의 북마크 삭제
    @Transactional
    public void deleteBookmark(BookmarkDeleteDto bookmarkDeleteDto) {
        // 북마크 존재 확인
        List<Bookmark> findBookmark = findVerifiedBookmark(bookmarkDeleteDto.getUser_id());

        // 북마크가 없다면 예외처리
        if(findBookmark.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND);
        }

        if(bookmarkDeleteDto.getBookmarkId() != null) {
            bookmarkRepository.deleteById(bookmarkDeleteDto.getBookmarkId());
            return;
        }

        // 유저 id만 있다면 해당 유저의 모든 북마크 삭제
        if(bookmarkDeleteDto.getPost_id() == null && bookmarkDeleteDto.getBookmark_name() == null) {
            bookmarkRepository.deleteByUsers_UserId(bookmarkDeleteDto.getUser_id());
            return;
        }

        // 포스트 id만 있다면 해당 유저의 해당 포스트 북마크 삭제
        if(bookmarkDeleteDto.getPost_id() != null && bookmarkDeleteDto.getBookmark_name() == null) {
            bookmarkRepository.deleteByUsers_UserIdAndPosts_PostId(bookmarkDeleteDto.getUser_id(), bookmarkDeleteDto.getPost_id());
            return;
        }

        // 북마크 이름만 있다면 해당 유저의 해당 북마크 삭제
        if(bookmarkDeleteDto.getPost_id() == null && bookmarkDeleteDto.getBookmark_name() != null) {
            bookmarkRepository.deleteByUsers_UserIdAndAndBookmarkName(bookmarkDeleteDto.getUser_id(), bookmarkDeleteDto.getBookmark_name());
            return;
        }

        // 포스트 id, 북마크 이름이 있다면 해당 북마크 삭제
        if(bookmarkDeleteDto.getPost_id() != null && bookmarkDeleteDto.getBookmark_name() != null) {
            bookmarkRepository.deleteByUsers_UserIdAndPosts_PostIdAndBookmarkName(bookmarkDeleteDto.getUser_id(), bookmarkDeleteDto.getPost_id(), bookmarkDeleteDto.getBookmark_name());
            return;
        }
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
