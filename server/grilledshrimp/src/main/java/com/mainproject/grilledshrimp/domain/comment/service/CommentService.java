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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

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
    //댓글 수정
    public Comment patchComment(Long commentId, CommentRequestDto requestDto) {
        // commentId에 해당하는 댓글을 데이터베이스에서 조회
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        // 수정할 내용을 requestDto에서 가져와서 업데이트
        existingComment.setComment_text(requestDto.getCommentText());

        // 수정된 댓글을 저장
        Comment updatedComment = commentRepository.save(existingComment);

        // 수정된 댓글을 CommentResponseDto로 변환하여 반환
        return CommentResponseDto.fromEntity(updatedComment);
    }

    // 특정 게시물에 대한 댓글 목록 조회
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPost_Id(postId);
    }
    //삭제
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}