package com.songsmily.petapi.shiro.enums;

public enum  LoginType {
    APIUSER("APIUser"),  REGUSER("REGUser"), ADMINUSER("AdminUser");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
    }