package com.example.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.backend.entities.UserAuth;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserAuth, String> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<UserAuth> findByEmail(String email);
    Optional<UserAuth> findByUsername(String username);
}
