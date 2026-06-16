package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.repository.ProductRepository;
import com.example.backend.dto.ProductRequest;
import com.example.backend.dto.ProductResponse;
import com.example.backend.entities.Product;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductResponse> getProducts() {

        return productRepository.findAll()
                .stream()
                .map(user -> new ProductResponse(user.getName(), user.getDescription(), user.getPrice(), user.getId()))
                .toList();

    }

    public ProductResponse getProduct(String id) { 
         Product product =  productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));
        return new ProductResponse(product.getName(), product.getDescription(), product.getPrice(), product.getUserId());
    }

    public ProductResponse createProduct(ProductRequest productRequest, String userId) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .userId(userId)
                .build();

        productRepository.save(product);

        return new ProductResponse(product.getName(), product.getDescription(), product.getPrice(),
                product.getUserId());

    }

    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
         Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));

         product.setName(productRequest.getName());
         product.setDescription(productRequest.getDescription());
         product.setPrice(productRequest.getPrice());
        //  product.setUserId(id);

        Product saved = productRepository.save(product);
        
        return new ProductResponse(saved.getName(),saved.getDescription(),saved.getPrice(),saved.getUserId());

    }

    public void deleteProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));
        productRepository.delete(product);
    }
}
