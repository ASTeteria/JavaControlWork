package javapring.javacw.dto;


public record JwtResponseDto(
        String accessToken,
        String refreshToken) {}