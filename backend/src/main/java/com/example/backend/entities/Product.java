package com.example.backend.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Document(collection = "product")
// @AllArgsConstructor
// @NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    String id;
    String name;
    String description;
    double price;
    String userId;
}
