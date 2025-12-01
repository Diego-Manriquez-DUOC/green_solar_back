package com.green_solar.green_solar_back.dto.Auth.Request;

public record UserUpdateRequestDTO(
    String username,
    String email,
    String password,
    String imgUrl
) {}