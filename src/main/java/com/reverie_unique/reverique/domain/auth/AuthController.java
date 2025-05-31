package com.reverie_unique.reverique.domain.auth;

import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.common.exception.BadRequestException;
import com.reverie_unique.reverique.domain.auth.Service.AuthService;
import com.reverie_unique.reverique.domain.auth.Service.EmailService;
import com.reverie_unique.reverique.domain.auth.Service.PasswordResetService;
import com.reverie_unique.reverique.domain.auth.dto.*;
import com.reverie_unique.reverique.domain.auth.entity.PasswordReset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final PasswordResetService passwordResetService;

    @Autowired
    public AuthController(AuthService authService, EmailService emailService, PasswordResetService passwordResetService){
        this.authService = authService;
        this.emailService = emailService;
        this.passwordResetService = passwordResetService;
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenDTO request) {
        return ResponseEntity.ok(authService.reissue(request));
    }
    @PostMapping("/email")
    public void sendEmail(@RequestBody EmailRequestDTO request){
        emailService.sendVerificationCode(request.getEmail());
    }

    @PostMapping("/verify-code")
    public ApiResponse<String> verifyEmailCode(@RequestBody EmailVerificationDTO request) {
        emailService.verifyCode(request.getEmail(), request.getCode()); // 예외 발생시 자동 처리됨
        return ApiResponse.success("인증 성공");
    }

    @PostMapping("/reset-password/request")
    public ResponseEntity<?> requestPasswordReset(@RequestParam String email) {
        passwordResetService.sendResetLink(email);
        return ResponseEntity.ok("비밀번호 재설정 링크를 이메일로 보냈습니다.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetDTO request) {
        boolean success = passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        if (!success) {
            return ResponseEntity.badRequest().body("토큰이 유효하지 않거나 만료되었습니다.");
        }
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
}