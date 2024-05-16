package com.example.finalboard.controller.response;

import java.time.LocalDateTime;

public record PostDeleteResponse(Long id, LocalDateTime deletedAt) {}
