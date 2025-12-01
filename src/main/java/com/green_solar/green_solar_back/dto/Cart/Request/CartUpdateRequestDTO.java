package com.green_solar.green_solar_back.dto.Cart.Request;

import java.util.List;

public record CartUpdateRequestDTO(
    String name,
    String description,
    List<Long> productIds
) {}