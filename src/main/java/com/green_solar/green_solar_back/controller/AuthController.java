package com.green_solar.green_solar_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green_solar.green_solar_back.dto.Auth.Request.UserLoginRequestDTO;
import com.green_solar.green_solar_back.dto.Auth.Request.UserRegisterRequestDTO;
import com.green_solar.green_solar_back.dto.Auth.Response.UserAuthResponseDTO;
import com.green_solar.green_solar_back.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserAuthResponseDTO> register(@RequestBody UserRegisterRequestDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponseDTO> login(@RequestBody UserLoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}