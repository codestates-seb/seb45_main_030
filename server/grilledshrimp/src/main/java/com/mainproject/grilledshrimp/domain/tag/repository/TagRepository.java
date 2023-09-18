package com.mainproject.grilledshrimp.domain.tag.repository;

import com.mainproject.grilledshrimp.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagByName(String tagName);
}
