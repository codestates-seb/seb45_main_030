package com.mainproject.grilledshrimp.domain.post.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

// 게시글 생성
@Data
public class PostsPostDto {
    private Long user_id;

    @NotNull
    @Length(min = 1, max = 100)
    private String post_title;
    private String post_address;
    private String post_caption;
    private boolean post_comment_permission;
    private List<String> tags;
    private List<String> post_image;

}
