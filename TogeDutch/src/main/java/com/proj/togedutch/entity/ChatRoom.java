package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class ChatRoom {
    private int chatRoomIdx;
    private Timestamp createdAt;


}
