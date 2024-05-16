package com.example.finalboard.dto;

import com.example.finalboard.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Long userId;  // 사용자 ID 필드 추가
    private String nickname; // 닉네임 추가
    private LocalDateTime createdAt;
    private Integer viewCount;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static PostDto from(Post entity) {
        return new PostDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getUser() != null ? entity.getUser().getUserId() : null, // null 체크 추가
                entity.getUser() != null ? entity.getUser().getUserNickname() : "알 수 없는 사용자", // null 체크 추가
                entity.getCreatedAt(),
                entity.getViewCount(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}

