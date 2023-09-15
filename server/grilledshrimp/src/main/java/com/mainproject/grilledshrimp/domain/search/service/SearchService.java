package com.mainproject.grilledshrimp.domain.search.service;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
import com.mainproject.grilledshrimp.domain.postTag.entity.PostTag;
import com.mainproject.grilledshrimp.domain.postTag.repository.PostTagRepository;
import com.mainproject.grilledshrimp.domain.tag.entity.Tag;
import com.mainproject.grilledshrimp.domain.tag.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SearchService {
    PostsRepository postsRepository;
    TagRepository tagRepository;
    PostTagRepository postTagRepository;

    public SearchService(PostsRepository postsRepository, TagRepository tagRepository, PostTagRepository postTagRepository) {
        this.postsRepository = postsRepository;
        this.tagRepository = tagRepository;
        this.postTagRepository = postTagRepository;
    }

    // 제목으로 검색
    public List<Posts> searchByTitle(String title) {
        log.info("searchByTitle: {}", title);
        return postsRepository.findByPostTitleLike("%" + title + "%");
    }

    // 태그로 검색
    public List<Posts> searchByTag(String tagName) {
        log.info("searchByTag: {}", tagName);
        List<Posts> postsList = new ArrayList<>();
        Optional<Tag> findTag = tagRepository.findTagByName(tagName);


        if (findTag.isPresent()) {
            Tag tag = findTag.get();
            Optional<List<PostTag>> postTagList = postTagRepository.findByTag_Id(tag.getId());
            if (postTagList.isPresent()) {
                List<PostTag> postTags = postTagList.get();
                for (PostTag postTag : postTags) {
                    Optional<Posts> posts = postsRepository.findById(postTag.getPosts().getPostId());
                    if (posts.isPresent()) {
                        postsList.add(posts.get());
                    }
                }
            }
        }

        return postsList;
    }
}
