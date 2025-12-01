package com.green_solar.green_solar_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.green_solar.green_solar_back.dto.Cart.Request.CartCreateRequestDTO;
import com.green_solar.green_solar_back.dto.Cart.Request.CartUpdateRequestDTO;
import com.green_solar.green_solar_back.dto.Cart.Response.CartResponseDTO;
import com.green_solar.green_solar_back.dto.Cart.Response.CartItemResponseDTO;
import com.green_solar.green_solar_back.dto.Product.Response.ProductResponseDTO;
import com.green_solar.green_solar_back.model.Cart;
import com.green_solar.green_solar_back.model.CartItem;
import com.green_solar.green_solar_back.model.Product;
import com.green_solar.green_solar_back.model.User;
import com.green_solar.green_solar_back.repository.CartRepository;
import com.green_solar.green_solar_back.repository.ProductRepository;
import com.green_solar.green_solar_back.repository.UserRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartResponseDTO createCart(Long userId, CartCreateRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = Cart.builder()
                .name(dto.name())
                .description(dto.description())
                .user(user)
                .cartItems(new ArrayList<>())
                .build();

        Cart savedCart = cartRepository.save(cart);
        
        if (dto.productIds() != null && !dto.productIds().isEmpty()) {
            updateCartItems(savedCart, dto.productIds());
        }
        
        return toResponseDTO(savedCart);
    }

    public List<CartResponseDTO> getUserCarts(Long userId) {
        return cartRepository.findByUserId(userId).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CartResponseDTO updateCart(Long cartId, Long userId, CartUpdateRequestDTO dto) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!cart.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied: Cart does not belong to user");
        }

        if (dto.name() != null) cart.setName(dto.name());
        if (dto.description() != null) cart.setDescription(dto.description());
        if (dto.productIds() != null) {
            updateCartItems(cart, dto.productIds());
        }

        Cart updatedCart = cartRepository.save(cart);
        return toResponseDTO(updatedCart);
    }

    public CartResponseDTO getCart(Long cartId, Long userId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!cart.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied: Cart does not belong to user");
        }

        return toResponseDTO(cart);
    }

    public void deleteCart(Long cartId, Long userId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (!cart.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied: Cart does not belong to user");
        }

        cartRepository.delete(cart);
    }

    private void updateCartItems(Cart cart, List<Long> productIds) {
        cart.getCartItems().clear();
        
        Map<Long, Long> productCounts = productIds.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        
        for (Map.Entry<Long, Long> entry : productCounts.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + entry.getKey()));
            
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(entry.getValue().intValue())
                    .build();
            
            cart.getCartItems().add(cartItem);
        }
        
        cartRepository.save(cart);
    }

    private CartResponseDTO toResponseDTO(Cart cart) {
        List<CartItemResponseDTO> itemDTOs = cart.getCartItems().stream()
                .map(this::toCartItemResponseDTO)
                .toList();

        return new CartResponseDTO(
            cart.getId(),
            cart.getName(),
            cart.getDescription(),
            cart.getUser().getId(),
            itemDTOs
        );
    }

    private CartItemResponseDTO toCartItemResponseDTO(CartItem cartItem) {
        ProductResponseDTO productDTO = new ProductResponseDTO(
            cartItem.getProduct().getId(),
            cartItem.getProduct().getName(),
            cartItem.getProduct().getDesc(),
            cartItem.getProduct().getPrice(),
            cartItem.getProduct().getCategory(),
            cartItem.getProduct().getStorageKW(),
            cartItem.getProduct().getProductionKW(),
            cartItem.getProduct().getImgUrl()
        );
        
        return new CartItemResponseDTO(
            cartItem.getId(),
            productDTO,
            cartItem.getQuantity()
        );
    }
}