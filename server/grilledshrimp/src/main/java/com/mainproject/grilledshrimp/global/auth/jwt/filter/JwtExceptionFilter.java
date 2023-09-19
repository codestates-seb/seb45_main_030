package com.mainproject.grilledshrimp.global.auth.jwt.filter;

import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (BusinessLogicException e) {
            log.info("BusinessLogicException");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(e.getExceptionCode().getStatus());
            response.getWriter().write(e.getExceptionCode().getMessage());
        } catch (Exception e) {
            log.info("Exception");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(ExceptionCode.INTERNAL_SERVER_ERROR.getStatus());
            response.getWriter().write(ExceptionCode.INTERNAL_SERVER_ERROR.getMessage());
        }
    }
}
