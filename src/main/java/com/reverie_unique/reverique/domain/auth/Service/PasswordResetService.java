package com.reverie_unique.reverique.domain.auth.Service;

import com.reverie_unique.reverique.domain.auth.entity.PasswordReset;
import com.reverie_unique.reverique.domain.auth.repository.PasswordResetRepository;
import com.reverie_unique.reverique.domain.user.entity.User;
import com.reverie_unique.reverique.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(UserRepository userRepository,
                                PasswordResetRepository passwordResetRepository,
                                EmailService emailService,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordResetRepository = passwordResetRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendResetLink(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return;
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(30);

        PasswordReset resetToken = new PasswordReset(token, user, expiry);
        passwordResetRepository.save(resetToken);
        // 이메일 전송 요청
        emailService.sendPasswordResetEmail(email, token);
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordReset> tokenOpt = passwordResetRepository.findByToken(token);
        if (tokenOpt.isEmpty()) return false;

        PasswordReset resetToken = tokenOpt.get();
        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetRepository.delete(resetToken); // 한 번 쓰고 삭제
        return true;
    }
}