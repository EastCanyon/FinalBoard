package com.example.finalboard.dto;

import com.example.finalboard.model.Comment;

public record CommentUpdateDto(
        Long id,
        String comment,
        String updatedAt
) {
    public static CommentUpdateDto from(Comment entity) {
        return new CommentUpdateDto(
                entity.getId(),
                entity.getComment(),
                entity.getUpdatedAt()
        );
    }
}
