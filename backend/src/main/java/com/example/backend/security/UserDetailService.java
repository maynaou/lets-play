package com.example.backend.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.backend.repository.UserRepository;
import com.example.backend.entities.UserAuth;
import com.example.backend.exception.UserNotFoundException;

@Service
public class UserDetailService implements UserDetailsService {
    
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
            UserAuth user = userRepository.findByEmail(identifier)
            .or(() -> userRepository.findByUsername(identifier))
            .orElseThrow(() ->
                    new UserNotFoundException(
                            "Username or password incorrect"));
         return new CustomUserDetails(user);
    }
    
}
