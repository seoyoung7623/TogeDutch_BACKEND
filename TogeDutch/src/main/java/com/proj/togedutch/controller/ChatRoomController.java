package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.ChatRoom;
import com.proj.togedutch.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "room";
    }

}
