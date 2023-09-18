package com.mainproject.grilledshrimp.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 태그 조회
@AllArgsConstructor
@Data
@NoArgsConstructor
public class TagResponseDto {
    private Long id;
    private String tag_name;
}
