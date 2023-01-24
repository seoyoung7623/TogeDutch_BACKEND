package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.dao.ChatRoomDao;
import com.proj.togedutch.entity.ChatRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ChatRoomDao chatRoomDao;
    @Autowired
    public ChatRoomService(ChatRoomDao chatRoomDao){
        this.chatRoomDao = chatRoomDao;
    }
    public ChatRoom createChatRoom() throws BaseException {
        try {
            int chatRoomIdx = chatRoomDao.createChatRoom();
            return chatRoomDao.getChatRoomById(chatRoomIdx);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public ChatRoom getChatRoomById(int chatRoomIdx) throws BaseException {
        try{
            return chatRoomDao.getChatRoomById(chatRoomIdx);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    public List<ChatRoom> getAllChatRooms() throws BaseException {
        try {
            return chatRoomDao.getAllChatRooms();
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int deleteChatRoom(int chatRoomIdx) throws BaseException{
        try {
            return chatRoomDao.deleteChatRoom(chatRoomIdx);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
