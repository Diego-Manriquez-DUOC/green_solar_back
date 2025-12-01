package com.green_solar.green_solar_back.dto.Cart.Response;

import java.util.List;

public record CartResponseDTO(
    Long id,
    String name,
    String description,
    Long userId,
    List<CartItemResponseDTO> items
) {}