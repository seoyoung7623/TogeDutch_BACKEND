package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ChatPhoto {
    private int chatPhotoId;
    private int chatRoomId;
    private int userId;
    private String image;
    private Timestamp createAt;
}
