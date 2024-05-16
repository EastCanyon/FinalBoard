package com.example.finalboard.dto;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DeleteDto {
    private Long postId;

    // 생성자, 게터 및 세터
    public DeleteDto(Long postId) {
        this.postId = postId;
    }

}

