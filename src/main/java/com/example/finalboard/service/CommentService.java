package com.example.finalboard.service;

import com.example.finalboard.dto.CommentDeleteDto;
import com.example.finalboard.dto.CommentDto;
import com.example.finalboard.dto.CommentUpdateDto;
import com.example.finalboard.model.Comment;
import com.example.finalboard.model.MyUser;
import com.example.finalboard.model.Post;
import com.example.finalboard.repository.CommentRepository;
import com.example.finalboard.repository.PostRepository;
import com.example.finalboard.repository.UserRepository;
import com.example.finalboard.util.DevBlogException;
import com.example.finalboard.util.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentDto createComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new DevBlogException(ExceptionCode.POST_NOT_FOUND));
        MyUser user = userRepository.findById(userId)
                .orElseThrow(() -> new DevBlogException(ExceptionCode.USER_NOT_FOUND));

        Comment comment = new Comment(content, post, user);
        comment = commentRepository.save(comment);
        return CommentDto.from(comment);
    }

    public CommentUpdateDto updateComment(Long commentId, CommentUpdateDto updateDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DevBlogException(ExceptionCode.POST_COMMENT_NOT_FOUND));
        comment.setComment(updateDto.comment());
        comment.update();  // Update timestamp
        comment = commentRepository.save(comment);
        return CommentUpdateDto.from(comment);
    }

    public CommentDeleteDto deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DevBlogException(ExceptionCode.POST_COMMENT_NOT_FOUND));
        comment.delete();  // Set deletedAt timestamp
        comment = commentRepository.save(comment);
        return CommentDeleteDto.from(comment);
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostIdAndNotDeleted(postId).stream()
                .map(CommentDto::from)
                .collect(Collectors.toList());
    }

}