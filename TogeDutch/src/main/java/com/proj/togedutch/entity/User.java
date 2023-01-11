package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private int keywordIdx;
    private String name;
    private String role;
    private String email;
    private String password;
    private String phone;
    private String location;
    //TODO:파일 추가
    private String status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String jwt;

    public User(int userIdx, int keywordIdx, String name, String role, String email, String password, String phone, String location, String status) {
        this.userIdx = userIdx;
        this.keywordIdx = keywordIdx;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.location = location;
        this.status = status;
    }
}
