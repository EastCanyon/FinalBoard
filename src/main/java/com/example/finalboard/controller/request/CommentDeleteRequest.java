package com.example.finalboard.controller.request;

import com.example.finalboard.dto.CommentDeleteDto;
import com.example.finalboard.dto.CommentDto;

public record CommentDeleteRequest(
        Long commentId
) {
    public CommentDeleteDto toDto() {
        return new CommentDeleteDto(
                this.commentId(),
                null
        );
    }
}