package com.example.finalboard.controller.response;

import com.example.finalboard.dto.CommentDeleteDto;

public record CommentDeleteResponse(
        Long id
) {
    public static CommentDeleteResponse from(CommentDeleteDto dto) {
        return new CommentDeleteResponse(
                dto.id()
        );
    }
}
