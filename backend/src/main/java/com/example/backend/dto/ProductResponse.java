package com.example.backend.dto;

public record ProductResponse(
        String name,
        String description,
        Double price,
        String userId
) {}
