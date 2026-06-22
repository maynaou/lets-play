package com.example.backend.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createBucket() {
        Bandwidth limit = Bandwidth.builder()
            .capacity(10)
            .refillIntervally(10, Duration.ofMinutes(1))
            .build();

        return Bucket.builder()
            .addLimit(limit)
            .build();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

              

        String uri = request.getRequestURI();
        System.out.println("URI reçue: " + uri);       
        boolean shouldLimit = uri.equals("/api/auth/login") || uri.equals("/api/auth/register");
        System.out.println("shouldLimit: " + shouldLimit); // ← ajoute ça

        if (!shouldLimit) {
            System.out.println("lllllllllllllllllll");
            filterChain.doFilter(request, response); 
            return;
        }

        String ip = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(ip, k -> createBucket());

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response); 
        } else {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("""
                        {
                            "status": 429,
                            "message": "Too many requests. Please wait 1 minute."
                        }
                    """);
        }
    }
}
