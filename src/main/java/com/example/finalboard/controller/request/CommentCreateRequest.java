package com.example.finalboard.controller.request;

import com.example.finalboard.dto.CommentDto;

public record CommentCreateRequest(
        String comment,
        Long postId,
        Long userId
) {
    public CommentDto toDto() {
        return new CommentDto(
                null,
                this.comment(),
                this.postId(),
                this.userId(),
                null,
                null,
                null,
                null
        );
    }
}
