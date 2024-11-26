package dev.kiki.forum.user.dto;

import java.util.Map;

public record LoginResponse(
        String message,
        String accessToken,
        Map<String, Object> user,
        Long expiresIn
) {
}
