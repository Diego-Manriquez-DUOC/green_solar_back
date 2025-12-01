package com.green_solar.green_solar_back.dto.Cart.Request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record CartCreateRequestDTO(
    @NotBlank String name,
    String description,
    List<Long> productIds
) {}