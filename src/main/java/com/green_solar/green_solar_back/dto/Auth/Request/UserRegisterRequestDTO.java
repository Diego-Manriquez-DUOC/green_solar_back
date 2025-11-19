package com.green_solar.green_solar_back.dto.Auth.Request;

public record UserRegisterRequestDTO(
    String email,
    String username,
    String password
) {
}
