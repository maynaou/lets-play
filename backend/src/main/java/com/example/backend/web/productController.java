package com.example.backend.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import com.example.backend.dto.ProductRequest;
import com.example.backend.dto.ProductResponse;
import com.example.backend.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class productController {

   @Autowired
   ProductService productService;

   @GetMapping
   public ResponseEntity<List<ProductResponse>> getProducts() {
      List<ProductResponse> products = productService.getProducts(); 
      return ResponseEntity.ok(products);
   }

   @GetMapping("/{id}")
   public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
      ProductResponse product = productService.getProduct(id); 
      return ResponseEntity.ok(product);
   }

   @PostMapping
   public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest, @AuthenticationPrincipal Jwt jwt) {
      String userId = jwt.getClaimAsString("userId");
      ProductResponse product = productService.createProduct(productRequest, userId);
      return ResponseEntity.ok(product);
   }

   @PutMapping("/{id}")
   public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
      ProductResponse product = productService.updateProduct(id, productRequest);
      return ResponseEntity.ok(product);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
          productService.deleteProduct(id);
         return ResponseEntity.noContent().build();
   }

}
