package com.example.finalboard.repository;

import com.example.finalboard.model.Comment;
import com.example.finalboard.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 모든 댓글 조회
    List<Comment> findByPost_Id(Long postId);

    // 댓글과 관련된 사용자 정보와 함께 댓글 조회
    @Query("SELECT c FROM Comment c JOIN FETCH c.post p JOIN FETCH c.user u WHERE p.id = :postId")
    Page<Comment> findAllWithUser(Pageable pageable, Long postId);

    // 특정 댓글 삭제 시 삭제된 시간 기록 (soft delete)
    @Transactional
    @Modifying
    @Query(value = "UPDATE Comment c SET c.deletedAt = DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') WHERE c.id = :commentId", nativeQuery = true)
    void softDeleteComment(Long commentId);

    // 삭제되지 않은 댓글만 조회
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.deletedAt IS NULL")
    List<Comment> findByPostIdAndNotDeleted(@Param("postId") Long postId);
}
