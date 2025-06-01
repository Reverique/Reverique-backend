package com.reverie_unique.reverique.domain.user.dto;

public class PasswordChangeReqDTO {
    private String currentPassword;
    private String newPassword;

    public PasswordChangeReqDTO() {}

    public PasswordChangeReqDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}