package com.reverie_unique.reverique.domain.auth.Service;

import com.reverie_unique.reverique.common.email.EmailSenderContext;
import com.reverie_unique.reverique.common.exception.BadRequestException;
import com.reverie_unique.reverique.domain.auth.entity.EmailVerification;
import com.reverie_unique.reverique.domain.auth.repository.EmailVerificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailService {

    private final EmailVerificationRepository verificationRepository;
    private final EmailSenderContext emailSenderContext;

    public EmailService(EmailVerificationRepository verificationRepository, EmailSenderContext emailSenderContext) {
        this.verificationRepository = verificationRepository;
        this.emailSenderContext = emailSenderContext;
    }

    public void sendVerificationCode(String email) {
        String code = UUID.randomUUID().toString().substring(0, 6);
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(10);
        EmailVerification verification = new EmailVerification(email, code, expiry);

        verificationRepository.findByEmail(email).ifPresent(existing -> verificationRepository.delete(existing));
        verificationRepository.save(verification);

        emailSenderContext.sendEmail("naverEmailSender", email, "Reverique Email Verification", "Code: " + code);
    }

    public boolean verifyCode(String email, String code) {
        EmailVerification verification = verificationRepository.findByEmailAndCode(email, code)
                .orElseThrow(() -> new BadRequestException("Invalid verification code"));
        if (verification.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Verification code expired");
        }

        verification.setVerified(true);
        verificationRepository.save(verification);
        return true;
    }

    public boolean isEmailVerified(String email) {
        return verificationRepository.findByEmail(email)
                .filter(ev -> ev.isVerified() && ev.getExpiryDate().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        String subject = "Reverique 비밀번호 재설정 안내";
        String resetLink = "https://your-domain.com/auth/reset-password?token=" + resetToken;
        String content = "안녕하세요,\n아래 링크를 클릭하여 비밀번호를 재설정하세요. 링크는 30분간 유효합니다.\n\n" + resetLink;

        // 발송 - emailSenderContext를 통해 실제 전송
        emailSenderContext.sendEmail("naverEmailSender", toEmail, subject, content);
    }
}