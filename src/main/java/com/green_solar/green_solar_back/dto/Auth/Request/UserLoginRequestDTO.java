package com.green_solar.green_solar_back.dto.Auth.Request;

public record UserLoginRequestDTO(
    String email,
    String password
) {}
