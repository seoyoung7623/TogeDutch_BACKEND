package com.proj.togedutch.entity;

import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
public class ChatMessage {
    public ChatMessage(){

    }

    private int chat_id;
    private int chatRoom_id;
    private int userId;
    private Timestamp createAt;
    private String content;
    private String status; //읽음 안읽음

    // String으로 변환
    private String roomId;
    private String writer;

    public ChatMessage(int chat_id, int chatRoom_chatRoom_id, int user_user_id, Timestamp created_at, String content) {
        this.chat_id = chat_id;
        this.chatRoom_id = chatRoom_chatRoom_id;
        this.userId = user_user_id;
        this.createAt = created_at;
        this.content = content;
    }

//    // 메시지 타입 : 입장, 퇴장, 채팅, 파일송신
//    public enum MessageType {
//        ENTER, QUIT, TALK,FILE
//    }

//    private MessageType type;


}


