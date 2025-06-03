package com.reverie_unique.reverique.domain.user.controller;

import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.common.jwt.JwtTokenProvider;
import com.reverie_unique.reverique.domain.user.dto.*;
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
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody UserSignupDTO request) {
        userService.signup(request);
        return ResponseEntity.ok(ApiResponse.success("회원가입 성공"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoDTO>> getMyInfo(HttpServletRequest request) {
        String token = resolveToken(request);

        if (token == null || !jwtProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.custom("fail", HttpStatus.UNAUTHORIZED.value(), "토큰이 유효하지 않습니다.", null));
        }

        Long userId = jwtProvider.getUserIdFromToken(token);

        UserInfoDTO userInfo = userService.getUserInfo(userId);

        return ResponseEntity.ok(ApiResponse.success(userInfo));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserUpdateResDTO>> updateMyInfo(HttpServletRequest request,
                                                                      @RequestBody UserUpdateReqDTO dto) {
        String token = resolveToken(request);

        if (token == null || !jwtProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.custom("fail", HttpStatus.UNAUTHORIZED.value(), "토큰이 유효하지 않습니다.", null));
        }

        Long userId = jwtProvider.getUserIdFromToken(token);

        try {
            UserUpdateResDTO updatedUser = userService.updateUserInfo(userId, dto);
            return ResponseEntity.ok(ApiResponse.success(updatedUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.custom("fail", HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
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
    public ResponseEntity<ApiResponse<Void>> deleteMyAccount(HttpServletRequest request) {
        String token = resolveToken(request);

        if (token == null || !jwtProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.custom("fail", HttpStatus.UNAUTHORIZED.value(), "토큰이 유효하지 않습니다.", null));
        }

        Long userId = jwtProvider.getUserIdFromToken(token);

        try {
            userService.deleteUser(userId);
            // 삭제 성공 시 204 No Content + 빈 바디 (ApiResponse 대신 빈 리턴 가능)
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.custom("fail", HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse<String>> changePassword(HttpServletRequest request,
                                                              @RequestBody PasswordChangeReqDTO dto) {
        String token = resolveToken(request);

        if (token == null || !jwtProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.custom("fail", HttpStatus.UNAUTHORIZED.value(), "토큰이 유효하지 않습니다.", null));
        }

        Long userId = jwtProvider.getUserIdFromToken(token);

        try {
            userService.changePassword(userId, dto);
            return ResponseEntity.ok(ApiResponse.success("비밀번호 변경 성공"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.custom("fail", HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }
}