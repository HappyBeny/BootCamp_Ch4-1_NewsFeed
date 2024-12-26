package com.example.ch4_1_newsfeed.domain;

public enum UserType {
    GUEST, MEMBER, ADMIN;


    public boolean isAdmin() {
        return this == ADMIN;
    }
}