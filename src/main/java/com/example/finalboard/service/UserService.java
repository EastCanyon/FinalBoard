package com.example.finalboard.service;

import com.example.finalboard.model.MyUser;

import java.util.Optional;

public interface UserService {
    MyUser createUser(MyUser user);
    Optional<MyUser> getUserByUserId(String userId);
    void deleteUser(String userId);
}