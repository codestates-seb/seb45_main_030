package com.mainproject.grilledshrimp.domain.comment.dto;

import com.mainproject.grilledshrimp.domain.user.entity.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CommentDTO {
    private Long userId;
    private String commentText;
    private Long commentId;

    // 추가 필드
    private int totalPages;
    private long totalElements;
    private int currentPage;

}
