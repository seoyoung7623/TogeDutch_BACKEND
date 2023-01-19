package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Chat {
    private int chat_id;
    private int ChatRoom_chatRoom_id;
    private int User_user_id;
    private Timestamp created_at;
    private String content;
    private int status;

    public Chat(String content, int chatRoom_chatRoom_id, Timestamp created_at) {
        this.content = content;
        this.ChatRoom_chatRoom_id = chatRoom_chatRoom_id;
        this.created_at = created_at;
    }


//    public Chat (String sender,String receiver,String content){
//        this.sender = sender;
//        this.receiver = receiver;
//        this.content = content;
//    }

}
