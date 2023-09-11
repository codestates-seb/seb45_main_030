package com.mainproject.grilledshrimp.domain.bookmark.repository;

import com.mainproject.grilledshrimp.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<List<Bookmark>> findByUsers_UserId(Long user_id);
    //List<Optional<Bookmark>> findByPost_PostId(Long post_id);
    Optional<List<Bookmark>> findByUsers_UserIdAndBookmarkName(Long user_id, String bookmark_name);
}
