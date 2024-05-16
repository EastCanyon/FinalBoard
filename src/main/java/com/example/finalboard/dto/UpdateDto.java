package com.example.finalboard.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDto {
    private Long postId;
    private String title;
    private String content;

    // 생성자, 게터 및 세터
    public UpdateDto(Long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }
}


