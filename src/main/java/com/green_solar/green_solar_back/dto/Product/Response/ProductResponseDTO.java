package com.green_solar.green_solar_back.dto.Product.Response;

import com.green_solar.green_solar_back.model.ProductCategory;

public record ProductResponseDTO(
    Long id,
    String name,
    String desc,
    Integer price,
    ProductCategory category,
    Integer storageKW,
    Integer productionKW,
    String imgUrl
) {}