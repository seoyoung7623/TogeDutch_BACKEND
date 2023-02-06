package com.proj.togedutch.entity;

import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
public class ChatMessage {
    public ChatMessage(){}
    private int chatId;
    private int chatRoomId;
    private int userId;
    private Timestamp createAt;
    private String content;
    private String writer;

    private MessageType type;

    public ChatMessage(int chat_id, int chatRoom_chatRoom_id, int user_user_id, Timestamp created_at, String content, String name) {
        this.chatId = chat_id;
        this.chatRoomId = chatRoom_chatRoom_id;
        this.userId = user_user_id;
        this.createAt = created_at;
        this.content = content;
        this.writer = name;
    }

    // 메시지 타입 : 입장, 퇴장, 채팅, 파일송신
    public enum MessageType {
        ENTER, QUIT, TALK
    }

}


