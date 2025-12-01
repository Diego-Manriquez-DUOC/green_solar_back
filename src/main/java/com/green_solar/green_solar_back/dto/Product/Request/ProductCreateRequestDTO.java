package com.green_solar.green_solar_back.dto.Product.Request;

import com.green_solar.green_solar_back.model.ProductCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreateRequestDTO(
    @NotBlank String name,
    String desc,
    @NotNull Integer price,
    @NotNull ProductCategory category,
    @NotNull Integer storageKW,
    @NotNull Integer productionKW
) {}