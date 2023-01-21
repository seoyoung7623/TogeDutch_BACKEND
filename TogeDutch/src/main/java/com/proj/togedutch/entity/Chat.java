package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Chat {
    private int chatIdx;
    private int ChatRoom_chatRoomIdx;
    private int User_userIdx;
    private Timestamp createdAt;
    private String content;
    private int status;

    public Chat(int chatIdx, int chatRoom_chatRoomIdx, int user_userIdx, String content, int status) {
        this.chatIdx = chatIdx;
        ChatRoom_chatRoomIdx = chatRoom_chatRoomIdx;
        User_userIdx = user_userIdx;
        this.content = content;
        this.status = status;
    }

    //    public Chat (String sender,String receiver,String content){
//        this.sender = sender;
//        this.receiver = receiver;
//        this.content = content;
//    }

}
