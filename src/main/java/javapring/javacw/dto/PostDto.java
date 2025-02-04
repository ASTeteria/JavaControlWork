package javapring.javacw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record PostDto(
        Long id,

        @NotBlank(message = "Text cannot be blank")
        @Size(min = 5, max = 500, message = "Text must be between 5 and 500 characters")
        String text,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long userId
) {}


