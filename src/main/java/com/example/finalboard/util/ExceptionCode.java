package com.example.finalboard.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    //USERS
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "No Such User"),
    MEMBER_EXISTS(HttpStatus.CONFLICT, "User Already Exists"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid Password"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "You Are Not Authorized"),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "No Such Post"),
    POST_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "Post Already Exists"),

    // POST COMMENT
    POST_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "No Such Post Comment"),

    // POST DELETE
    CAN_NOT_DELETE(HttpStatus.UNAUTHORIZED, "User is not authorized to delete this post"),

    // Server
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final HttpStatus status;
    private final String message;

}