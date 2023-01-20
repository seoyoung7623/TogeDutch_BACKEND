package com.proj.togedutch.entity;

import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
public class ChatMessage {
    public ChatMessage(){

    }

    // 메시지 타입 : 입장, 퇴장, 채팅, 파일송신
    public enum MessageType {
        ENTER, QUIT, TALK,FILE
    }

    private MessageType type;
    private int chatRoom_id;
    private int user_id;
    private String content;
    private String status;
    //private Timestamp created_at;

    @Builder
    public ChatMessage(MessageType type,int chatRoom_id,int user_id,String content){
        this.type = type;
        this.chatRoom_id = chatRoom_id;
        this.user_id = user_id;
        this.content = content;
    }


}


