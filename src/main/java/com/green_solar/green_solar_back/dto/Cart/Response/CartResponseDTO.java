package com.green_solar.green_solar_back.dto.Cart.Response;

import java.util.List;

import com.green_solar.green_solar_back.dto.Product.Response.ProductResponseDTO;

public record CartResponseDTO(
    Long id,
    String name,
    String description,
    Long userId,
    List<ProductResponseDTO> products
) {}