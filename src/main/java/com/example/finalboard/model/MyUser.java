package com.example.finalboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Entity
@Table(name = "user")
@Getter
public class MyUser {

    // ID 설정은 Long 타입을 사용
    // 닉네임, 이메일을 제외한 나머지는 세터 사용금지
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email", nullable = false, length = 30)
    private String userEmail;

    @Column(name = "user_pw", nullable = false, length = 100)
    private String userPw;

    @Column(name = "user_nickname", nullable = false, length = 70)
    private String userNickname;

    @Enumerated(EnumType.STRING) // 열거형을 문자열로 저장
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Column(name = "created_at", nullable = false, columnDefinition = "VARCHAR(14)")
    private String createdAt;

    @Column(name = "updated_at", columnDefinition = "VARCHAR(14)")
    private String updatedAt;

    @Column(name = "deleted_at", columnDefinition = "VARCHAR(14)")
    private String deletedAt;

    // 기본 생성자에서 userRole을 USER로 초기화
    public MyUser() {
        this.userRole = UserRole.USER;  // 기본적으로 USER 역할 할당
    }
    // 사용자 정보를 초기화하는 생성자
    public MyUser(String userEmail, String userPassword, String userNickname) {
        this();
        this.userEmail = userEmail;
        this.userPw = userPassword;
        this.userNickname = userNickname;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.userRole = UserRole.USER; // UserRole 열거형을 사용
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
        if (userRole == null) {
            this.userRole = UserRole.USER; // null 경우에 대비하여 다시 한번 USER 할당
        }
    }

    public String getUsername() {
        return userEmail;
    }

    public String getPassword() {
        return userPw;
    }

    public void setPassword(String password) {
        this.userPw = password;
    }

    public String getNickname() {
        return userNickname;
    }


}
