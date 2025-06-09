package model.dto;

public record UserResponseDto(
    String uuid,
    String name,
    String email
) { }
