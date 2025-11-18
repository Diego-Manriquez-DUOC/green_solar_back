package com.green_solar.green_solar_back.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.green_solar.green_solar_back.dto.User.Request.UserLoginRequestDTO;
import com.green_solar.green_solar_back.dto.User.Request.UserRegisterRequestDTO;
import com.green_solar.green_solar_back.dto.User.Response.UserAuthResponseDTO;
import com.green_solar.green_solar_back.dto.User.Response.UserInfoResponseDTO;
import com.green_solar.green_solar_back.model.User;
import com.green_solar.green_solar_back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAuthResponseDTO register(UserRegisterRequestDTO dto) {
        String hashedPassword = passwordEncoder.encode(dto.password());

        User user = User.builder()
                .email(dto.email())
                .password(hashedPassword)
                .username(dto.username())
                .build();

        userRepository.save(user);

        return new UserAuthResponseDTO(
            user.getId(),
            null,
            user.getUsername(),
            user.getImgUrl()
        );
    }

    public UserAuthResponseDTO login(UserLoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return new UserAuthResponseDTO(
            user.getId(),
            null,
            user.getUsername(),
            user.getImgUrl()
        );
    }

    public UserInfoResponseDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserInfoResponseDTO(
            user.getUsername(),
            user.getEmail(),
            user.getImgUrl()
        );
    }

}
