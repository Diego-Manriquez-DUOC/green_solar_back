package com.green_solar.green_solar_back.dto.Auth.Response;

public record UserAuthResponseDTO(
    Long id,
    String token,
    String username,
    String imgUrl
) {}
