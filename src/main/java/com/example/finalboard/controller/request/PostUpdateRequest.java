package com.example.finalboard.controller.request;

import com.example.finalboard.dto.PostDto;

import java.time.LocalDateTime;

public record PostUpdateRequest(String title, String content) {
    public PostDto toDto(Long postId, Long userId, LocalDateTime updatedAt, Integer viewCount) {
        return new PostDto(
                postId,
                title,
                content,
                userId,
                null,
                LocalDateTime.now(),
                viewCount,
                updatedAt,
                null
        );
    }
}
