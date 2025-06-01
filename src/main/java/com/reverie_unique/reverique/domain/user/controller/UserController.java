package com.reverie_unique.reverique.domain.user.controller;

import com.reverie_unique.reverique.common.jwt.JwtTokenProvider;
import com.reverie_unique.reverique.domain.user.dto.UserInfoDTO;
import com.reverie_unique.reverique.domain.user.dto.UserSignupDTO;
import com.reverie_unique.reverique.domain.user.dto.UserUpdateReqDTO;
import com.reverie_unique.reverique.domain.user.dto.UserUpdateResDTO;
import com.reverie_unique.reverique.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")

public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupDTO request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> getMyInfo(HttpServletRequest request) {
        String token = resolveToken(request);

        if (token == null || !jwtProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = jwtProvider.getUserIdFromToken(token);

        UserInfoDTO userInfo = userService.getUserInfo(userId);

        return ResponseEntity.ok(userInfo);
    }

    @PutMapping("/me")
    public ResponseEntity<UserUpdateResDTO> updateMyInfo(HttpServletRequest request,
                                                         @RequestBody UserUpdateReqDTO dto) {
        String token = resolveToken(request);

        if (token == null || !jwtProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = jwtProvider.getUserIdFromToken(token);

        try {
            UserUpdateResDTO updatedUser = userService.updateUserInfo(userId, dto);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(HttpServletRequest request) {
        String token = resolveToken(request);

        if (token == null || !jwtProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = jwtProvider.getUserIdFromToken(token);

        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}