package com.green_solar.green_solar_back.dto.Auth.Response;

public record UserInfoResponseDTO(
    String username,
    String email,
    String img_url
) {}
