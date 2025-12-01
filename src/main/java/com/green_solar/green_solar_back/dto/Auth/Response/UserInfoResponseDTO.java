package com.green_solar.green_solar_back.dto.Auth.Response;

import com.green_solar.green_solar_back.model.UserRole;

public record UserInfoResponseDTO(
    String username,
    UserRole role,
    String email,
    String imgUrl
) {}
