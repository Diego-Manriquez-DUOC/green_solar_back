package com.green_solar.green_solar_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green_solar.green_solar_back.model.Product;
import com.green_solar.green_solar_back.model.ProductCategory;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    List<Product> findByCategory(ProductCategory category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByNameContainingIgnoreCaseAndCategory(String name, ProductCategory category);
}