package com.green_solar.green_solar_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green_solar.green_solar_back.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
}