package com.dungbui.microservices.product.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dungbui.microservices.product.dto.ProductRequest;
import com.dungbui.microservices.product.dto.ProductResponse;
import com.dungbui.microservices.product.model.Product;
import com.dungbui.microservices.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductResponse createProduct(ProductRequest request) {
        // Convert ProductRequest to Product and save it
        Product product = new Product(
            request.id(), 
            request.name(), 
            request.description(), 
            request.price().doubleValue());
        productRepository.save(product);
        log.info("Product created successfully");
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            BigDecimal.valueOf(product.getPrice()));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), BigDecimal.valueOf(product.getPrice())))
                .toList();
    }
}
