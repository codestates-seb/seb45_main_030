package com.mainproject.grilledshrimp.domain.comment.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentRequestDto;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import com.mainproject.grilledshrimp.domain.comment.repository.CommentRepository;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostsRepository postRepository;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            UserRepository userRepository,
            PostsRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto) {
        // 게시글, 사용자 정보 가져오기 (user_id는 세션 또는 로그인 정보에서 얻을 수 있음)
        Posts post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        Users user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        // 댓글 생성
        Comment comment = new Comment();
        comment.setPosts(post);
        comment.setUsers(user);
        comment.setComment_text(requestDto.getComment_text());
        comment.setCreated_at(LocalDateTime.now());
        comment.setModified_at(LocalDateTime.now());

        comment = commentRepository.save(comment);

        // 생성된 댓글을 응답 DTO로 변환
        CommentResponseDto responseDto = new CommentResponseDto();
        responseDto.setComment_id(comment.getComment_id());
        responseDto.setPost_id(comment.getPosts().getPost_id());
        responseDto.setUser_id(comment.getUsers().getUser_id());
        responseDto.setComment_text(comment.getComment_text());
        responseDto.setCreated_at(comment.getCreated_at());
        responseDto.setModified_at(comment.getModified_at());

        return responseDto;
    }
}