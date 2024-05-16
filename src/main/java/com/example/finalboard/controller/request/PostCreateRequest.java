package com.example.finalboard.controller.request;

import com.example.finalboard.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


public record PostCreateRequest(String title, String content, Integer viewCount) {
    public PostDto toDto() {
        return new PostDto(
                null,
                this.title(),
                this.content(),
                null,
                null,
                null,
                this.viewCount,
                null,
                null
        );
    }
}