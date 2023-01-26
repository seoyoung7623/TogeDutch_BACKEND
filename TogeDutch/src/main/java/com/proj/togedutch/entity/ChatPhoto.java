package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ChatPhoto {
    private int chatPhoto_id;
    private Timestamp created_at;
    private int chatRoom_id;
    private int user_id;
    private String image;
}
