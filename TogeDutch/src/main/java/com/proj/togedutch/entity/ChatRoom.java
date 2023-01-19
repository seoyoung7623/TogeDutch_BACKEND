package com.proj.togedutch.entity;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ChatRoom {
    private int chatRoom_id;
    private Timestamp created_at;

    @Builder
    public ChatRoom(int chatRoom_id,Timestamp created_at){
        this.chatRoom_id = chatRoom_id;
        this.created_at = created_at;
    }


}
