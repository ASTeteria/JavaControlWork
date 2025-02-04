package javapring.javacw.dto;


import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record ErrorDto(
        String message,
        String details,
        LocalDateTime timestamp
) {}
