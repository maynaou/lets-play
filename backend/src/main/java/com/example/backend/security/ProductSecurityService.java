package com.example.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.repository.ProductRepository;

@Service("productSecurity")
public class ProductSecurityService {
     
    @Autowired
    ProductRepository productRepository;

    public boolean isOwner(String productId, String currentId) {
        return productRepository.findById(productId).map(product -> product.getUserId().equals(currentId)).orElse(false); 
    } 
    
}
