package com.example.backend.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
   public String getProducts() {
      return "get products";
   }

   @GetMapping("/{id}")
   public String getProduct(@PathVariable String id) {
      return "product by id acces public";
   }

   @PostMapping
   public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest, @AuthenticationPrincipal Jwt jwt) {
      System.out.println("product name" + productRequest.getName() + " distription " + productRequest.getDescription()
            + " price " + productRequest.getPrice());
      // System.out.println(authentication.getName());


      String userId = jwt.getClaimAsString("userId");

      ProductResponse product = productService.createProduct(productRequest, userId);

      System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffff");

      return ResponseEntity.ok(product);
   }

}
