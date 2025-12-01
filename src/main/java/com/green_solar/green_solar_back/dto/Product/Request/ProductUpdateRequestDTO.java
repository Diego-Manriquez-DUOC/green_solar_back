package com.green_solar.green_solar_back.dto.Product.Request;

import com.green_solar.green_solar_back.model.ProductCategory;

public record ProductUpdateRequestDTO(
    String name,
    String desc,
    Integer price,
    ProductCategory category,
    Integer storageKW,
    Integer productionKW
) {}