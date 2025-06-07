package com.reverie_unique.reverique.domain.user.dto;

import com.reverie_unique.reverique.domain.user.entity.User;

public class UserInfoDTO {
    private Long id;
    private String email;
    private String name;
    private String nickName;
    private String birth;
    private String gender;
    private String profile;
    private PartnerInfoDTO partnerInfo;

    public PartnerInfoDTO getPartnerInfo() {
        return partnerInfo;
    }

    public void setPartnerInfo(PartnerInfoDTO partnerInfo) {
        this.partnerInfo = partnerInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserInfoDTO(Long id, String email, String name, String nickName, String birth, String gender, String profile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.birth = birth;
        this.gender = gender;
        this.profile = profile;
    }

    public UserInfoDTO(Long id, String email, String name, String nickName, String birth, String gender, String profile, PartnerInfoDTO partnerInfo) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.birth = birth;
        this.gender = gender;
        this.profile = profile;
        this.partnerInfo = partnerInfo;
    }

}
