package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.dao.AdDao;
import com.proj.togedutch.dao.ChatDao;
import com.proj.togedutch.entity.Chat;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.repo.ChatRoomRepository;
import com.proj.togedutch.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatRoom")
public class ChatController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatService chatService;
    private final ChatDao chatDao;

    @Autowired
    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

}
