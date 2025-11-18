package com.green_solar.green_solar_back.dto.User.Request;

public record UserRegisterRequestDTO(
    String email,
    String username,
    String password
) {
}
