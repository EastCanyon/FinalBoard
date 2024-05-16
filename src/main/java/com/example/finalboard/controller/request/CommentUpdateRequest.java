package com.example.finalboard.controller.request;

import com.example.finalboard.dto.CommentDto;
import com.example.finalboard.dto.CommentUpdateDto;

public record CommentUpdateRequest(Long commentId, String comment) {
    public CommentUpdateDto toDto() {
        return new CommentUpdateDto(
                this.commentId(),
                this.comment(),
                null
        );
    }
}
