package com.example.finalboard.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        log.error("[유효성 검사] error: {}", errorMessage);
        return ApiResult.error(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResult<Void> runtimeExceptionHandler(RuntimeException e) {
        log.error("[RuntimeException] {}", e.getMessage(), e);
        return ApiResult.error("Internal Server Error");
    }

    @ExceptionHandler(DevBlogException.class)
    public ApiResult<Void> devBlogExceptionHandler(DevBlogException e) {
        log.error("[DevBlogException] {}", e.getMessage(), e);
        return ApiResult.error(e.getMessage());
    }

    // 이 메서드만 남겨두고 나머지 일반 Exception 처리 메서드를 제거
    @ExceptionHandler(Exception.class)
    public ApiResult<Void> handleAllExceptions(Exception e) {
        log.error("Unhandled Exception: {}", e.getMessage(), e);
        return ApiResult.error("A server error occurred");
    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ApiResult<Void> runtimeExceptionHandler(RuntimeException e) {
//        String errorMessage = e.getMessage();
//        log.error("[RuntimeException] error: {}", errorMessage);
//
//        return ApiResult.error(errorMessage);
//    }
//
//    @ExceptionHandler(DevBlogException.class)
//    public ApiResult<Void> devBlogExceptionHandler(DevBlogException e) {
//        String errorMessage = e.getMessage();
//        log.error("[RuntimeException] error: {}", errorMessage);
//
//        return ApiResult.error(errorMessage);
//    }
//
//    //시큐리티 부분 삭제 후 모든 예외처리
//    @ExceptionHandler(Exception.class)
//    public ApiResult<Void> handleAllExceptions(Exception e) {
//        log.error("Exception: {}", e.getMessage());
//
//        return ApiResult.error(e.getMessage());
//    }

}
