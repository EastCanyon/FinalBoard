package com.example.finalboard.controller.response;

import com.example.finalboard.dto.CommentUpdateDto;

public record CommentUpdateResponse(
        //Long commentid 를 써야되나?
        Long id,
        String comment
) {
    public static CommentUpdateResponse from(CommentUpdateDto dto) {
        return new CommentUpdateResponse(
                dto.id(),
                dto.comment()
        );
    }
}
