package com.green_solar.green_solar_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green_solar.green_solar_back.repository.UserRepository;
import com.green_solar.green_solar_back.dto.Auth.Response.UserInfoResponseDTO;
import com.green_solar.green_solar_back.model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDTO> getCurrentUser(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserInfoResponseDTO response = new UserInfoResponseDTO(
            user.getUsername(),
            user.getRole(),
            user.getEmail(),
            user.getImg_url()
        );
        
        return ResponseEntity.ok(response);
    }
}