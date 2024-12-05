package dev.kiki.forum.tag.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTagDto(
        @NotBlank(message = "Tag title is required")
        String title,

        @NotBlank(message = "Tag description is required")
        String description
) {
}
