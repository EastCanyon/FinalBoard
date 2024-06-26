package com.example.finalboard.controller.request;

public record LoginRequest(String email, String password) {
    public String getEmail() {
        return email();
    }

    public String getPassword() {
        return password();
    }
}