package com.reverie_unique.reverique.domain.user.dto;

public class UserUpdateResDTO {
    private UserInfoDTO user;
    private String newAccessToken;

    public UserUpdateResDTO(UserInfoDTO user, String newAccessToken) {
        this.user = user;
        this.newAccessToken = newAccessToken;
    }

    public UserInfoDTO getUser() {
        return user;
    }

    public String getNewAccessToken() {
        return newAccessToken;
    }
}