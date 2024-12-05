package dev.kiki.forum.tag.dto;

import java.util.UUID;

public record TagResponseDto(
        UUID id,
        String title,
        String description,
        Long totalQuestions,
        Long questionsToday,
        Long questionsThisWeek
) {
}
