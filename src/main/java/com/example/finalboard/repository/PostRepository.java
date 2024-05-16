package com.example.finalboard.repository;

import com.example.finalboard.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    // 게시글을 "삭제"하는 대신 deleted_at에 타임스탬프를 기록
    // 게시글 소프트 삭제 쿼리
    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.deletedAt = CURRENT_TIMESTAMP WHERE p.id = :postId AND p.deletedAt IS NULL")
    void softDelete(Long postId);

    // 삭제되지 않은 게시물과 사용자 정보를 함께 조회
    @Query("SELECT p FROM Post p JOIN p.user u WHERE p.deletedAt IS NULL")
    Page<Post> findAllWithUser(Pageable pageable);

    // 조회수 증가 쿼리
    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :postId")
    void incrementViewCount(Long postId);
}
