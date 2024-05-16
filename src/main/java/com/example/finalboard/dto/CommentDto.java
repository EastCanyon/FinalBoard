package com.example.finalboard.dto;

import com.example.finalboard.model.Comment;

public record CommentDto(
        Long id,
        String comment,
        Long postId,
        Long userId,
        String nickname,
        String createdAt,
        String updatedAt,
        String deletedAt
) {
    public static CommentDto from(Comment entity) {
        return new CommentDto(
                entity.getId(),
                entity.getComment(),
                entity.getPostId(),
                entity.getUserId(),
                entity.getUser().getNickname(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
