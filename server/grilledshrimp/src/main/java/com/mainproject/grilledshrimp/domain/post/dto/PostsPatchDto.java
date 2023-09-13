package com.mainproject.grilledshrimp.domain.post.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

// 게시글 수정
@Data
public class PostsPatchDto {
    private Long userId;
    private Long postId;
    private String postCaption;
    private boolean postCommentPermission;
    private List<String> tags;
}
