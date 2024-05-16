package com.example.finalboard.controller.response;


import com.example.finalboard.dto.PostDto;

import java.time.LocalDateTime;

public record PostCreateResponse(
        Long id,
        String title,
        String content,
        String nickname,
        LocalDateTime createdAt,
        Integer viewCount,
        LocalDateTime updateAt,
        LocalDateTime deleteAt
) {
    public static PostCreateResponse from(PostDto postDto) {
        return new PostCreateResponse(
                postDto.getId(),
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getNickname(),
                postDto.getCreatedAt(),
                postDto.getViewCount(),
                postDto.getUpdatedAt(),
                postDto.getDeletedAt()
        );
    }
}
