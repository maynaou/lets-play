package com.example.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.backend.entities.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    
}
