package model.dto;

public record UserloginDto(
    String email,
    String password,
    String status
) { }
