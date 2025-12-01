package com.green_solar.green_solar_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green_solar.green_solar_back.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}