package com.example.finalboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "post_comment")
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Setter
    @Column(nullable = false, length = 300)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private MyUser user;

    @Column(name = "created_at", nullable = false, columnDefinition = "VARCHAR(14)")
    private String createdAt;

    @Column(name = "updated_at", columnDefinition = "VARCHAR(14)")
    private String updatedAt;

    @Column(name = "deleted_at", columnDefinition = "VARCHAR(14)")
    private String deletedAt;

    public Comment(String comment, Post post, MyUser user) {
        this.comment = comment;
        this.post = post;
        this.user = user;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.updatedAt = null;
        this.deletedAt = null;
    }

    public Long getPostId() {
        return post != null ? post.getId() : null;
    }

    public Long getUserId() {
        return user != null ? user.getUserId() : null;  // 예를 들어 MyUser 클래스에 getUserId()가 있다고 가정
    }

    public Comment() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public void update() {
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}