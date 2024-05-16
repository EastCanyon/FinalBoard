package com.example.finalboard.controller.response;

import java.time.LocalDateTime;

public record PostUpdateResponse(Long id, String content, LocalDateTime updatedAt) {}
