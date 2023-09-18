package com.mainproject.grilledshrimp.global.exception.response;

import com.google.gson.Gson;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import com.mainproject.grilledshrimp.global.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.io.IOException;

public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }

    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status, AuthenticationException exception) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(status);
        errorResponse.setMessage(exception.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status.value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }
}
