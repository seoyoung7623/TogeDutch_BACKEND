package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.dao.ChatDao;
import com.proj.togedutch.entity.ChatRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ChatDao chatDao;
    @Autowired
    public ChatRoomService(ChatDao chatDao){

        this.chatDao = chatDao;
    }
    public ChatRoom createChatRoom() {
        int chatRoomIdx = chatDao.createChatRoom();
        return chatDao.getChatRoomById(chatRoomIdx);
    }
}
