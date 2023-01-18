package com.proj.togedutch.entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    private int userIdx;
    private int keywordIdx;
    private String name;
    private String role;
    private String email;
    private String password;
    private String phone;
    private String image;
    private String status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private double latitude;
    private double longitude;
    private String jwt;

    public User(int userIdx, int keywordIdx, String name, String role, String email, String password, String phone, String image, double latitude, double longitude, String status, Timestamp created_at, Timestamp updated_at, String jwt) {
        this.userIdx = userIdx;
        this.keywordIdx = keywordIdx;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.image = image;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.latitude = latitude;
        this.longitude = longitude;
        this.jwt = jwt;
    }

    public User(int userIdx, int keywordIdx, String name, String role, String email, String password, String phone, double latitude, double longitude, String image, String status, String jwt) {
        this.userIdx = userIdx;
        this.keywordIdx = keywordIdx;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.image = image;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.jwt = jwt;
    }
}
