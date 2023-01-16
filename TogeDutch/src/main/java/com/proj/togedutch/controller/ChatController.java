package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.Chat;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    ChatService chatService;

//    @MessageMapping ing("/enter")
//    public ChatMessage enter(ChatMessage chatMessage){
//        System.out.println("enter method");
//    }

    @MessageMapping("/enter/{userIdx}")
    public BaseResponse<Chat> sendMessage(@PathVariable("userIdx") int userIdx, @RequestBody Chat chat){
        try {
            Chat chatnew = chatService.cheatemessage(chat,userIdx);
        }

    }


}
