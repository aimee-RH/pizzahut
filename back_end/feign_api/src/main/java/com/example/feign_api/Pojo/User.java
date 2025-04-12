package com.example.feign_api.Pojo;

import javax.xml.crypto.Data;

public class User {
    private String id;
    private String name;
    private String gender;
    private String phone;
    private String membershipLevel;
    private String password;
    private Data membershipExpiry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Data getMembershipExpiry() {
        return membershipExpiry;
    }

    public void setMembershipExpiry(Data membershipExpiry) {
        this.membershipExpiry = membershipExpiry;
    }
}
