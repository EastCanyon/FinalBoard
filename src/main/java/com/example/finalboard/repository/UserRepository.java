package com.example.finalboard.repository;

import com.example.finalboard.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUserEmail(String email);
    boolean existsByUserEmail(String email);
}

