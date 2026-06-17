package com.example.backend.dto;

import com.example.backend.entities.Product;

public record ProductResponse(
        String name,
        String description,
        Double price,
        String userId
) {
   public static ProductResponse fromProduct(Product product) {
        return new ProductResponse(
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getUserId()
        );
    }
}
