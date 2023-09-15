package com.mainproject.grilledshrimp.domain.bookmark.repository;

import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<List<Bookmark>> findByUsers_UserId(Long user_id);
    Optional<List<Bookmark>> findByPosts_PostId(Long post_id);


    // 특정 유저 특정 북마크 이름 폴더 조회
    Optional<List<Bookmark>> findByUsers_UserIdAndBookmarkName(Long user_id, String bookmark_name);
    
    // 특정 유저 특정 북마크 조회
    Optional<Bookmark> findByUsers_UserIdAndPosts_PostId(Long user_id, Long post_id);
}
