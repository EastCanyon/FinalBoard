package com.example.finalboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Setter
    @Column(nullable = false, length = 255)
    private String title;

    @Setter
    @Column(nullable = false, length = 255)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private MyUser user;  // User 엔티티 직접 참조

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // 추가된 메서드
    @Setter
    @Column(name = "view_count", nullable = false)
    private Integer  viewCount;

    @Setter
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP DEFAULT NULL")
    private LocalDateTime deletedAt;

    protected Post() {}

    public Post(String title, String content, MyUser user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.viewCount = 1;
    }

    public static Post of(String title, String content, MyUser user) {
        return new Post(title, content, user);
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getNickname() {
        return user != null ? user.getNickname() : null;
    }
}
