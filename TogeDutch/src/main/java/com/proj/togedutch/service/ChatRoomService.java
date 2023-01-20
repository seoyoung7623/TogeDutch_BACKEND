package com.proj.togedutch.service;

import com.proj.togedutch.dao.ChatRoomDao;
import com.proj.togedutch.entity.ChatRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ChatRoomDao chatRoomDao;
    @Autowired
    public ChatRoomService(ChatRoomDao chatRoomDao){

        this.chatRoomDao = chatRoomDao;
    }
    public ChatRoom createChatRoom() {
        int chatRoomIdx = chatRoomDao.createChatRoom();
        return chatRoomDao.getChatRoomById(chatRoomIdx);
    }
}
