package com.mainproject.grilledshrimp.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    // 공통
    INVALID_INPUT_VALUE(400, "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(405, "허용되지 않은 메소드입니다."),
    INTERNAL_SERVER_ERROR(500, "서버에 오류가 발생했습니다."),
    INVALID_TYPE_VALUE(400, "잘못된 타입입니다."),

    // JWT 토큰 및 인증
    AUTHORIZATION_NULL(401, "인증 정보가 없습니다."),
    AUTHORIZATION_NOT_BEARER(401, "Bearer로 시작하지 않는 인증 정보입니다."),
    AUTHENTICATION_FAILED(401, "인증에 실패했습니다."),
    JWT_TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),
    JWT_TOKEN_INVALID(401, "토큰이 유효하지 않습니다."),
    JWT_TOKEN_NOT_FOUND(401, "토큰이 존재하지 않습니다."),


    // 게시글
    POST_NOT_FOUND(404, "해당 게시글을 찾을 수 없습니다."),
    
    // 댓글
    COMMENT_NOT_FOUND(404, "해당 댓글을 찾을 수 없습니다."),
    
    // 유저
    USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다."),
    USER_LOGIN_FAILED(401, "로그인에 실패했습니다."),
    USER_LOGIN_REQUIRED(401, "로그인이 필요합니다."),

    USER_LOGOUT_FAILED(401, "로그아웃에 실패했습니다."),
    USER_LOGOUTED(401, "이미 로그아웃된 유저입니다."),

    USER_CREATE_FAILED(500, "유저 생성에 실패했습니다."),
    USER_EMAIL_DUPLICATION(409, "이미 존재하는 이메일입니다."),
    USER_NICKNAME_DUPLICATION(409, "이미 존재하는 닉네임입니다."),
    USER_PASSWORD_NOT_MATCHED(401, "비밀번호가 일치하지 않습니다."),
    USER_PASSWORD_CHANGE_FAILED(500, "비밀번호 변경에 실패했습니다."),

    
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
