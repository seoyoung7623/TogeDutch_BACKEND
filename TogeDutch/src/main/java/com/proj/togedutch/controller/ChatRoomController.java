package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.ChatRoom;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.service.ChatRoomService;
import com.proj.togedutch.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;

@RestController
@RequestMapping("/chatRoom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService){
        this.chatRoomService = chatRoomService;
    }

    // 채팅방 생성
    @PostMapping("")
    public BaseResponse<ChatRoom> createChatRoom(){
        ChatRoom chatRoom = chatRoomService.createChatRoom();
        return new BaseResponse<>(chatRoom);
    }

}
