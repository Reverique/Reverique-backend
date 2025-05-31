package com.reverie_unique.reverique.domain.auth.entity;

import com.reverie_unique.reverique.domain.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity

public class PasswordReset {

    @Id
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    private LocalDateTime expirationDate;

    // 기본 생성자 (JPA용)
    public PasswordReset() {}

    // 전체 필드 생성자
    public PasswordReset(String token, User user, LocalDateTime expirationDate) {
        this.token = token;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    // Getter/Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    // equals / hashCode (token 기준으로)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordReset)) return false;
        PasswordReset that = (PasswordReset) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "token='" + token + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}