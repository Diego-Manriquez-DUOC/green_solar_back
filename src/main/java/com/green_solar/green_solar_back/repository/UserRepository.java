package com.green_solar.green_solar_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green_solar.green_solar_back.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
