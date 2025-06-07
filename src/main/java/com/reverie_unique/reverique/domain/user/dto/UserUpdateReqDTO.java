package com.reverie_unique.reverique.domain.user.dto;

public class UserUpdateReqDTO {
    private String nickname;
    private String birthDate;
    private String gender;
    private String password; // 현재 비밀번호
    private String profile;

    public String getNickname() {
        return nickname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }

    public UserUpdateReqDTO() {}

    public UserUpdateReqDTO(String nickname, String birthDate, String gender, String password, String profile) {
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.profile = profile;
    }
}
