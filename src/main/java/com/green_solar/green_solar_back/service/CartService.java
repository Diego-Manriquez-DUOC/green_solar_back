package com.green_solar.green_solar_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.green_solar.green_solar_back.dto.Cart.Request.CartCreateRequestDTO;
import com.green_solar.green_solar_back.dto.Cart.Request.CartUpdateRequestDTO;
import com.green_solar.green_solar_back.dto.Cart.Response.CartResponseDTO;
import com.green_solar.green_solar_back.dto.Product.Response.ProductResponseDTO;
import com.green_solar.green_solar_back.model.Cart;
import com.green_solar.green_solar_back.model.Product;
import com.green_solar.green_solar_back.model.User;
import com.green_solar.green_solar_back.repository.CartRepository;
import com.green_solar.green_solar_back.repository.ProductRepository;
import com.green_solar.green_solar_back.repository.UserRepository;

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

        List<Product> products = productRepository.findAllById(dto.productIds());

        Cart cart = Cart.builder()
                .name(dto.name())
                .description(dto.description())
                .user(user)
                .products(products)
                .build();

        Cart savedCart = cartRepository.save(cart);
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
            List<Product> products = productRepository.findAllById(dto.productIds());
            cart.setProducts(products);
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

    private CartResponseDTO toResponseDTO(Cart cart) {
        List<ProductResponseDTO> productDTOs = cart.getProducts().stream()
                .map(this::toProductResponseDTO)
                .toList();

        return new CartResponseDTO(
            cart.getId(),
            cart.getName(),
            cart.getDescription(),
            cart.getUser().getId(),
            productDTOs
        );
    }

    private ProductResponseDTO toProductResponseDTO(Product product) {
        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getDesc(),
            product.getPrice(),
            product.getCategory(),
            product.getStorageKW(),
            product.getProductionKW()
        );
    }
}