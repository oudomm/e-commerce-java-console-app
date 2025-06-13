package model.dto;

public record UserCreateDto(
    String name,
    String email,
    String password
) { }
