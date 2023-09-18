package com.mainproject.grilledshrimp.domain.comment.service;

import com.mainproject.grilledshrimp.domain.comment.dto.CommentDTO;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentPatchDto;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentPostDto;
import com.mainproject.grilledshrimp.domain.comment.dto.CommentResponseDto;
import com.mainproject.grilledshrimp.domain.comment.entity.Comment;
import com.mainproject.grilledshrimp.domain.comment.repository.CommentRepository;
import com.mainproject.grilledshrimp.domain.post.entity.Posts;
import com.mainproject.grilledshrimp.domain.post.repository.PostsRepository;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import com.mainproject.grilledshrimp.domain.user.repository.UserRepository;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostsRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public CommentResponseDto addComment(CommentPostDto commentPostDto, Long postId) {
        // 1. 사용자 엔티티 조회
        Users user = userRepository.findByUserId(commentPostDto.getUserId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        // 2. 게시물 엔티티 조회
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        // 3. 댓글 엔티티 생성 및 필드 설정
        Comment comment = new Comment();
        comment.setUsers(user);
        comment.setPosts(post);
        comment.setCommentText(commentPostDto.getCommentText());
        LocalDateTime currentTime = LocalDateTime.now();
        comment.setCreatedAt(currentTime);
        comment.setModifiedAt(currentTime);

        commentRepository.save(comment);

        return CommentResponseDto.of(comment);
    }
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentPatchDto commentPatchDto) {
        // commentId로 댓글을 조회하여 업데이트
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        Users user = userRepository.findByUserId(commentPatchDto.getUserId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        comment.setCommentText(commentPatchDto.getCommentText());
        comment.setModifiedAt(LocalDateTime.now());
        commentRepository.save(comment);

        CommentResponseDto commentResponseDto = CommentResponseDto.of(comment);
        // 수정된 댓글을 저장
        return commentResponseDto;
    }
    @Transactional
    public void deleteComment(Long commentId) {
        // commentId로 댓글을 조회하여 삭제
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }

    public Page<CommentResponseDto> getCommentsByPostId(Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPosts_PostId(postId, pageable);
        return comments.map(CommentResponseDto::of);
    }
}