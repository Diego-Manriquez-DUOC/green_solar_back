package com.green_solar.green_solar_back.dto.Cart.Response;

import com.green_solar.green_solar_back.dto.Product.Response.ProductResponseDTO;

public record CartItemResponseDTO(
    Long id,
    ProductResponseDTO product,
    Integer quantity
) {}