package com.mainproject.grilledshrimp.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    // 게시글
    POST_NOT_FOUND(404, "해당 게시글을 찾을 수 없습니다."),
    
    // 댓글
    COMMENT_NOT_FOUND(404, "해당 댓글을 찾을 수 없습니다."),
    ILLIGAL_IMAGE_TYPE(400, "잘못된 파일 형식입니다."),
    // 유저
    USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다."),
    
    // 이미지
    IMAGE_NOT_FOUND(404, "해당 이미지를 찾을 수 없습니다."),
    IMAGE_UPLOAD_FAILED(500, "이미지 업로드에 실패했습니다.");

    private int status;
    private String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }

}
