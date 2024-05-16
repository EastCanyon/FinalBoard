package com.example.finalboard.controller;

import com.example.finalboard.controller.request.CommentCreateRequest;
import com.example.finalboard.controller.request.CommentUpdateRequest;
import com.example.finalboard.controller.response.CommentCreateResponse;
import com.example.finalboard.controller.response.CommentDeleteResponse;
import com.example.finalboard.controller.response.CommentUpdateResponse;
import com.example.finalboard.dto.CommentDeleteDto;
import com.example.finalboard.dto.CommentDto;
import com.example.finalboard.dto.CommentUpdateDto;
import com.example.finalboard.service.CommentService;
import com.example.finalboard.util.ApiResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<ApiResult<CommentDto>> createComment(@RequestBody CommentCreateRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResult.error("User not authenticated"));
        }
        CommentDto createdComment = commentService.createComment(request.postId(), userId, request.comment());
        return ResponseEntity.ok(ApiResult.success(createdComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<CommentUpdateResponse>> updateComment(@PathVariable Long id, @RequestBody CommentUpdateRequest request) {
        CommentUpdateDto updateDto = commentService.updateComment(id, request.toDto());
        return ResponseEntity.ok(ApiResult.success(CommentUpdateResponse.from(updateDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<CommentDeleteResponse>> deleteComment(@PathVariable Long id) {
        CommentDeleteDto deleteDto = commentService.deleteComment(id);
        return ResponseEntity.ok(ApiResult.success(CommentDeleteResponse.from(deleteDto)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResult<List<CommentDto>>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(ApiResult.success(comments));
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<CommentDto>>> getCommentsByPostId(@RequestParam Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(ApiResult.success(comments));
    }
}