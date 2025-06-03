package com.reverie_unique.reverique.domain.user.dto;

public class PartnerInfoDTO {
    private String name;
    private String nickname;
    private String birthDate;
    private String gender;
    private String startDate;

    public PartnerInfoDTO() {
    }

    public PartnerInfoDTO(String name, String nickname, String birthDate, String gender, String startDate) {
        this.name = name;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}