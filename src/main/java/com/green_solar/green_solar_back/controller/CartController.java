package com.green_solar.green_solar_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green_solar.green_solar_back.dto.Cart.Request.CartCreateRequestDTO;
import com.green_solar.green_solar_back.dto.Cart.Request.CartUpdateRequestDTO;
import com.green_solar.green_solar_back.dto.Cart.Response.CartResponseDTO;
import com.green_solar.green_solar_back.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponseDTO> createCart(
            @Valid @RequestBody CartCreateRequestDTO dto,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.createCart(userId, dto));
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getUserCarts(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(cartService.getUserCarts(userId));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> getCart(
            @PathVariable Long cartId,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(cartService.getCart(cartId, userId));
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> updateCart(
            @PathVariable Long cartId,
            @RequestBody CartUpdateRequestDTO dto,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(cartService.updateCart(cartId, userId, dto));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(
            @PathVariable Long cartId,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        cartService.deleteCart(cartId, userId);
        return ResponseEntity.noContent().build();
    }
}