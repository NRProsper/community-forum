package dev.kiki.forum.user.dto;

public record RegisterResponse(
        String message,
        UserInfoDto user

) {
}
