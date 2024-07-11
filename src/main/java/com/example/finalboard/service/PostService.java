package com.example.finalboard.service;

import com.example.finalboard.dto.PostDto;
import com.example.finalboard.model.MyUser;
import com.example.finalboard.model.Post;
import com.example.finalboard.repository.PostRepository;
import com.example.finalboard.repository.UserRepository;
import com.example.finalboard.util.DevBlogException;
import com.example.finalboard.util.ExceptionCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<PostDto> getAllPosts(Specification<Post> spec, Pageable pageable) {
        Page<Post> posts = postRepository.findAll(spec, pageable);
        return posts.map(PostDto::from);
    }

    public Optional<PostDto> getPostById(Long id) {
        Optional<PostDto> postDto = postRepository.findById(id)
                .map(PostDto::from);
        postDto.ifPresent(dto -> log.info("Retrieved post: {}", dto)); // 로그 추가
        return postDto;
    }

    public PostDto createPost(PostDto dto, Long userId) {
        log.info("Creating post for user ID: {}", userId);
        MyUser userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new DevBlogException(ExceptionCode.USER_NOT_FOUND));
        log.info("Found user: {}", userEntity.getUserId());
        Post newPost = Post.of(dto.getTitle(), dto.getContent(), userEntity);
        newPost.setViewCount(dto.getViewCount() != null ? dto.getViewCount() : 0); // viewCount 설정
        newPost = postRepository.save(newPost);
        log.info("Post created with ID: {}", newPost.getId());
        return PostDto.from(newPost);
    }

    public PostDto updatePost(Long postId, String title, String content, Long userId) {
        log.info("Updating post ID: {} for user ID: {}", postId, userId);
        MyUser user = userRepository.findById(userId)
                .orElseThrow(() -> new DevBlogException(ExceptionCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new DevBlogException(ExceptionCode.POST_NOT_FOUND));

        post.setTitle(title);
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());  // 현재 시간으로 업데이트 시간 설정
        post = postRepository.save(post);
        log.info("Post updated with ID: {}", post.getId());
        return PostDto.from(post);
    }

    @Transactional
    public void incrementViewCount(Long postId) {
        postRepository.incrementViewCount(postId);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        log.info("Attempting to soft delete post ID: {} for user ID: {}", postId, userId);
        MyUser user = userRepository.findById(userId)
                .orElseThrow(() -> new DevBlogException(ExceptionCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new DevBlogException(ExceptionCode.POST_NOT_FOUND));

        if (!post.getUser().getUserId().equals(userId)) {
            throw new DevBlogException(ExceptionCode.CAN_NOT_DELETE);
        }

        postRepository.softDelete(postId);
        log.info("Post soft deleted with ID: {}", postId);
    }

    // 전체 행 수를 계산하는 메서드
    public long getTotalRowCount(String baseQuery) {
        String countQueryStr = "SELECT COUNT(*) " + baseQuery.substring(baseQuery.toLowerCase().indexOf("from"));
        Query countQuery = entityManager.createNativeQuery(countQueryStr);
        return ((Number) countQuery.getSingleResult()).longValue();
    }

    // 페이징된 데이터를 가져오는 메서드
    public List<PostDto> getPagedPosts(String pagingQuery) {
        Query query = entityManager.createNativeQuery(pagingQuery, Post.class);
        List<Post> posts = query.getResultList();
        return posts.stream().map(PostDto::from).toList();
    }
}
