package com.example.finalboard.controller.response;

import com.example.finalboard.dto.CommentDto;
import com.example.finalboard.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostGetResponse {

    private PostDto postDto;
    private List<CommentDto> commentDtoList;

}
