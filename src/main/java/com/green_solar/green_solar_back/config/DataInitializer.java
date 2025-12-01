package com.green_solar.green_solar_back.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.green_solar.green_solar_back.model.User;
import com.green_solar.green_solar_back.model.UserRole;
import com.green_solar.green_solar_back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@greensolar.com")) {
            User admin = User.builder()
                    .email("admin@greensolar.com")
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .build();
            
            userRepository.save(admin);
            System.out.println("Admin user created: admin@greensolar.com / admin123");
        }
    }
}