package com.reverie_unique.reverique.domain.auth.repository;

import com.reverie_unique.reverique.domain.auth.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, String> {
    Optional<PasswordReset> findByToken(String token);
}