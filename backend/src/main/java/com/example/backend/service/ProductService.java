package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.repository.ProductRepository;
import com.example.backend.dto.ProductRequest;
import com.example.backend.dto.ProductResponse;
import com.example.backend.entities.Product;
import com.example.backend.exception.ProductNotFoundException;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductResponse> getProducts() {

        return productRepository.findAll()
                .stream()
                .map(ProductResponse::fromProduct)
                .toList();

    }

    public ProductResponse getProduct(String id) { 
         Product product =  productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found"));
        return ProductResponse.fromProduct(product);
    }

    public ProductResponse createProduct(ProductRequest productRequest, String userId) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .userId(userId)
                .build();
        return ProductResponse.fromProduct(productRepository.save(product));
    }

    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
         Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found with id: " + id));

         product.setName(productRequest.getName());
         product.setDescription(productRequest.getDescription());
         product.setPrice(productRequest.getPrice());

        return ProductResponse.fromProduct(productRepository.save(product));
    }

    public void deleteProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found with id: " + id));
        productRepository.delete(product);
    }
}
