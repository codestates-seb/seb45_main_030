package com.mainproject.grilledshrimp.domain.search.controller;

import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.mapper.PostsMapper;
import com.mainproject.grilledshrimp.domain.search.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;
    private final PostsMapper postsMapper;

    public SearchController(SearchService searchService, PostsMapper postsMapper) {
        this.searchService = searchService;
        this.postsMapper = postsMapper;
    }

    // 제목으로 검색
    @GetMapping("/title")
    public ResponseEntity searchByTitle(@RequestParam String title) {
        List<Posts> postsList = searchService.searchByTitle(title);
        return ResponseEntity.ok(postsMapper.postsToPostsResponseSimpleDtos(postsList));
    }

    // 태그로 검색
    @GetMapping("/tag")
    public ResponseEntity searchByTag(@RequestParam String tags) {
        List<Posts> postsList = searchService.searchByTag(tags);
        return ResponseEntity.ok(postsMapper.postsToPostsResponseSimpleDtos(postsList));
    }
}
