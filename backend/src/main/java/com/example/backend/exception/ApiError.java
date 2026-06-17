package com.example.backend.exception;

public record ApiError(
                String code,
                String message) {
}