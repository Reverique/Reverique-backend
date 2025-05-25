package com.reverie_unique.reverique.domain.auth;

import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.common.exception.BadRequestException;
import com.reverie_unique.reverique.domain.auth.Service.AuthService;
import com.reverie_unique.reverique.domain.auth.Service.EmailService;
import com.reverie_unique.reverique.domain.auth.dto.EmailRequestDTO;
import com.reverie_unique.reverique.domain.auth.dto.EmailVerificationDTO;
import com.reverie_unique.reverique.domain.auth.dto.LoginDTO;
import com.reverie_unique.reverique.domain.auth.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @Autowired
    public AuthController(AuthService authService, EmailService emailService){
        this.authService = authService;
        this.emailService = emailService;
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
}