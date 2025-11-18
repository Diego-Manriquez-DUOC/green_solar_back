package com.green_solar.green_solar_back.dto.User.Request;

public record UserLoginRequestDTO(
    String email,
    String password
) {}
