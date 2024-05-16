package com.example.finalboard.controller;


import com.example.finalboard.controller.request.PostCreateRequest;
import com.example.finalboard.controller.request.PostUpdateRequest;
import com.example.finalboard.controller.response.PostGetResponse;
import com.example.finalboard.dto.CommentDto;
import com.example.finalboard.dto.PostDto;
import com.example.finalboard.model.Post;
import com.example.finalboard.repository.PostRepository;
import com.example.finalboard.service.CommentService;
import com.example.finalboard.service.PostService;
import com.example.finalboard.service.PostSpecification;
import com.example.finalboard.util.ApiResult;
import com.example.finalboard.util.DevBlogException;
import com.example.finalboard.util.ExceptionCode;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final PostRepository postRepository;

    @PostMapping
    public ResponseEntity<ApiResult<PostDto>> createPost(@Validated @RequestBody PostCreateRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResult.error("User not authenticated"));
        }
        PostDto createdPost = postService.createPost(request.toDto(), userId);
        return ResponseEntity.ok(ApiResult.success(createdPost));
    }

    @GetMapping
    public ResponseEntity<ApiResult<Page<PostDto>>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort) {

        Sort sortOptions = createSortOption(sort != null ? sort.split(",") : new String[]{"createdAt", "desc"});
        Pageable pageable = PageRequest.of(page, pageSize, sortOptions);
        Specification<Post> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(PostSpecification.titleOrContentContains(search));
        }

        Page<PostDto> postsPage = postService.getAllPosts(spec, pageable);
        System.out.println("Serving page: " + page + " with size: " + pageSize);
        System.out.println("Search: " + search);
        return ResponseEntity.ok(ApiResult.success(postsPage));
    }


    private Sort createSortOption(String[] sortDetails) {
        if (sortDetails.length < 2) {
            return Sort.by("createdAt").descending(); // 기본 정렬
        }
        try {
            return Sort.by(Sort.Direction.fromString(sortDetails[1]), sortDetails[0]);
        } catch (IllegalArgumentException e) {
            return Sort.by("createdAt").descending(); // 잘못된 정렬 옵션 처리
        }
    }


    @GetMapping("/{postId}")
    public ApiResult<PostGetResponse> getPostById(@PathVariable Long postId) {
        log.info("Fetching post with ID: {}", postId);

        // 조회수 증가 처리
        postService.incrementViewCount(postId);

        // 게시글 불러오기
        PostDto postDto = postService.getPostById(postId).orElseThrow(() ->
                new IllegalArgumentException("Post not found"));
        log.info("Retrieved post details: {}", postDto);

        // 댓글 불러오기
        List<CommentDto> commentDtoList = commentService.getCommentsByPostId(postId);

        PostGetResponse postGetResponse = new PostGetResponse(postDto, commentDtoList);
        return ApiResult.success(postGetResponse);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResult<PostDto>> updatePost(@PathVariable Long postId, @RequestBody @Valid PostUpdateRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResult.error("User not authenticated"));
        }
        PostDto updatedPost = postService.updatePost(postId, request.title(), request.content(), userId);
        return ResponseEntity.ok(ApiResult.success(updatedPost));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResult<Void>> deletePost(@PathVariable Long postId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResult.error("User not authenticated"));
        }

        postService.deletePost(postId, userId);  // userId도 함께 전달
        return ResponseEntity.ok(ApiResult.success());
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResult<Page<PostDto>>> getPosts(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort,
            Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(
                Arrays.stream(sort).map(s -> {
                    String[] parts = s.split(",");
                    return new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0]);
                }).collect(Collectors.toList())
        ));

        Specification<Post> spec = Specification.where(PostSpecification.titleOrContentContains(search));
        // postRepository 인스턴스를 사용하여 findAll 메소드 호출
        Page<Post> posts = postRepository.findAll(spec, pageable);
        return ResponseEntity.ok(ApiResult.success(posts.map(PostDto::from)));
    }
}
