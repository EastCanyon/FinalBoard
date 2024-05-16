package com.example.finalboard.controller.response;

import com.example.finalboard.dto.CommentDto;

public record CommentCreateResponse(
        Long id,
        String comment,
        Long postId,
        Long userId
) {
    public static CommentCreateResponse from(CommentDto dto) {
        return new CommentCreateResponse(
                dto.id(),
                dto.comment(),
                dto.postId(),
                dto.userId()
        );
    }
}