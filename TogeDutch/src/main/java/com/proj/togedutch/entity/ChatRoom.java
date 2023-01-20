package com.proj.togedutch.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class ChatRoom {
    private int chatRoomIdx;
    private Timestamp createdAt;

    @Builder
    public ChatRoom(int chatRoomIdx, Timestamp createdAt){
        this.chatRoomIdx = chatRoomIdx;
        this.createdAt = createdAt;
    }


}
