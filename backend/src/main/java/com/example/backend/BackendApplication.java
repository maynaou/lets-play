package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.backend.repository.UserRepository;
import com.example.backend.entities.UserAuth;



@SpringBootApplication
public class BackendApplication {
    
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


	// @Bean
	public CommandLineRunner init() {
		return args -> {
			// Clear the database on startup
			UserAuth userAuth = UserAuth.builder()
					.email("admin@example.com")
					.username("admin")
					.password(passwordEncoder.encode("admin"))
					.role("ADMIN")
					.build();
			userRepository.save(userAuth);
		};
	}



}
