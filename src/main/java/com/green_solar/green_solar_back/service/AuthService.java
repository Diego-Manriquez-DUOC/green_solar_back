package com.green_solar.green_solar_back.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.green_solar.green_solar_back.dto.Auth.Request.UserLoginRequestDTO;
import com.green_solar.green_solar_back.dto.Auth.Request.UserRegisterRequestDTO;
import com.green_solar.green_solar_back.dto.Auth.Response.UserAuthResponseDTO;
import com.green_solar.green_solar_back.dto.Auth.Response.UserInfoResponseDTO;
import com.green_solar.green_solar_back.exception.EmailAlreadyExistsException;
import com.green_solar.green_solar_back.model.User;
import com.green_solar.green_solar_back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserAuthResponseDTO register(UserRegisterRequestDTO dto) {
        if(userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(dto.password());

        User user = User.builder()
                .email(dto.email())
                .password(hashedPassword)
                .username(dto.username())
                .build();

        
        userRepository.save(user);

        String token = jwtService.generateToken(user.getUser_id());
        
        return new UserAuthResponseDTO(
            user.getUser_id(),
            token,
            user.getUsername(),
            user.getImg_url()
        );
    }

    public UserAuthResponseDTO login(UserLoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getUser_id());
        
        return new UserAuthResponseDTO(
            user.getUser_id(),
            token,
            user.getUsername(),
            user.getImg_url()
        );
    }

    public UserInfoResponseDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserInfoResponseDTO(
            user.getUsername(),
            user.getEmail(),
            user.getImg_url()
        );
    }

}
