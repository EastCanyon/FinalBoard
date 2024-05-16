package com.example.finalboard.controller.request;

public record SignupRequest(String email, String password, String nickname) {
    public String getEmail() {
        return email();
    }

    public String getPassword() {
        return password();
    }

    public String getNickname() {
        return nickname();
    }
}