package com.example.backend.exception;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationErrors(
                        MethodArgumentNotValidException ex) {

                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(errors);
        }

        @ExceptionHandler(UsernameAlreadyExistsException.class)
        public ResponseEntity<ApiError> handleUsernameExists(UsernameAlreadyExistsException ex) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ApiError("USERNAME_TAKEN", ex.getMessage()));
        }

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<ApiError> handleEmailExists(EmailAlreadyExistsException ex) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ApiError("EMAIL_TAKEN", ex.getMessage()));
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(new ApiError("UNAUTHORIZED", ex.getMessage()));
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) {
                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(new ApiError("UNAUTHORIZED", "Username or password incorrect"));
        }

        @ExceptionHandler(ProductNotFoundException.class)
        public ResponseEntity<ApiError> handleProductNotFound(ProductNotFoundException ex) {
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(new ApiError("NOT_FOUND", ex.getMessage()));
        }

//             @ExceptionHandler(UnauthorizedException.class)
//     public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
//         return ResponseEntity
//             .status(HttpStatus.UNAUTHORIZED)
//             .body(new ErrorResponse(401, ex.getMessage()));
//     }

}
