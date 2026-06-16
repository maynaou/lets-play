package com.example.backend.dto;

import lombok.Data;

@Data
public class ProductRequest {
    String name;
    String description; 
    double price;
}
