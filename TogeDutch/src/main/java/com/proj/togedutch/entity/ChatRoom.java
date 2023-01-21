package com.proj.togedutch.entity;

import com.proj.togedutch.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

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
