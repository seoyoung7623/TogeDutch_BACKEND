package com.proj.togedutch.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class ChatRoom implements Serializable {
    private int chatRoom_id;
    private Timestamp created_at;

    public ChatRoom(int chatRoom_id,Timestamp created_at){
        this.chatRoom_id = chatRoom_id;
        this.created_at = created_at;
    }


}
