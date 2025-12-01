package com.green_solar.green_solar_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.green_solar.green_solar_back.dto.Product.Request.ProductCreateRequestDTO;
import com.green_solar.green_solar_back.dto.Product.Request.ProductUpdateRequestDTO;
import com.green_solar.green_solar_back.dto.Product.Response.ProductResponseDTO;
import com.green_solar.green_solar_back.exception.ProductNotFoundException;
import com.green_solar.green_solar_back.model.Product;
import com.green_solar.green_solar_back.model.ProductCategory;
import com.green_solar.green_solar_back.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return toResponseDTO(product);
    }

    public ProductResponseDTO createProduct(ProductCreateRequestDTO dto) {
        Product product = Product.builder()
                .name(dto.name())
                .desc(dto.desc())
                .price(dto.price())
                .category(dto.category())
                .storageKW(dto.storageKW())
                .productionKW(dto.productionKW())
                .imgUrl(dto.imgUrl())
                .build();
        
        Product savedProduct = productRepository.save(product);
        return toResponseDTO(savedProduct);
    }

    public ProductResponseDTO updateProduct(Long id, ProductUpdateRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        
        if (dto.name() != null) product.setName(dto.name());
        if (dto.desc() != null) product.setDesc(dto.desc());
        if (dto.price() != null) product.setPrice(dto.price());
        if (dto.category() != null) product.setCategory(dto.category());
        if (dto.storageKW() != null) product.setStorageKW(dto.storageKW());
        if (dto.productionKW() != null) product.setProductionKW(dto.productionKW());
        if (dto.imgUrl() != null) product.setImgUrl(dto.imgUrl());
        
        Product updatedProduct = productRepository.save(product);
        return toResponseDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public List<ProductResponseDTO> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductCategory[] getAllCategories() {
        return ProductCategory.values();
    }

    public List<ProductResponseDTO> searchProducts(String name, ProductCategory category) {
        List<Product> products;
        
        if (name != null && category != null) {
            products = productRepository.findByNameContainingIgnoreCaseAndCategory(name, category);
        } else if (name != null) {
            products = productRepository.findByNameContainingIgnoreCase(name);
        } else if (category != null) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findAll();
        }
        
        return products.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getDesc(),
            product.getPrice(),
            product.getCategory(),
            product.getStorageKW(),
            product.getProductionKW(),
            product.getImgUrl()
        );
    }
}
