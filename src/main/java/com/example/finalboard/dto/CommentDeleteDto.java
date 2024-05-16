package com.example.finalboard.dto;

import com.example.finalboard.model.Comment;

import java.time.LocalDateTime;

public record CommentDeleteDto(
        Long id,
        String deletedAt
) {
    public static CommentDeleteDto from(Comment entity) {
        return new CommentDeleteDto(
                entity.getId(),
                entity.getDeletedAt()
        );
    }
}
