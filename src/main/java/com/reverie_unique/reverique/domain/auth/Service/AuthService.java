package com.reverie_unique.reverique.domain.auth.Service;

import com.reverie_unique.reverique.common.jwt.JwtTokenProvider;
import com.reverie_unique.reverique.domain.auth.dto.LoginDTO;
import com.reverie_unique.reverique.domain.auth.dto.TokenDTO;
import com.reverie_unique.reverique.domain.auth.entity.RefreshToken;
import com.reverie_unique.reverique.domain.auth.repository.RefreshTokenRepository;
import com.reverie_unique.reverique.domain.user.entity.User;
import com.reverie_unique.reverique.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service

public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       JwtTokenProvider jwtTokenProvider,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenDTO login(LoginDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        // 기존 토큰 삭제 (있으면)
        refreshTokenRepository.findByUserId(user.getId())
                .ifPresent(existingToken -> refreshTokenRepository.delete(existingToken));

        RefreshToken newToken = new RefreshToken();
        newToken.setUserId(user.getId());
        newToken.setToken(refreshToken);
        newToken.setExpiryDate(new Date(System.currentTimeMillis() + jwtTokenProvider.getRefreshTokenExpiry()));
        refreshTokenRepository.save(newToken);

        return new TokenDTO(accessToken, refreshToken);
    }

    @Transactional
    public TokenDTO reissue(TokenDTO request) {
        Long userId = jwtTokenProvider.getUserIdFromToken(request.getRefreshToken());
        RefreshToken storedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (!storedToken.getToken().equals(request.getRefreshToken()) ||
                !jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccess = jwtTokenProvider.generateAccessToken(user);
        String newRefresh = jwtTokenProvider.generateRefreshToken(user);

        storedToken.setToken(newRefresh);
        storedToken.setExpiryDate(new Date(System.currentTimeMillis() + jwtTokenProvider.getRefreshTokenExpiry()));

        refreshTokenRepository.save(storedToken);

        return new TokenDTO(newAccess, newRefresh);
    }
}