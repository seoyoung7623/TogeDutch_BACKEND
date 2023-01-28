package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.dao.ApplicationDao;
import com.proj.togedutch.dao.ChatMessageDao;
import com.proj.togedutch.dao.ChatRoomDao;
import com.proj.togedutch.dao.PostDao;
import com.proj.togedutch.entity.ChatRoom;
import com.proj.togedutch.entity.Post;
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
    private ChatMessageDao chatMessageDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private ApplicationDao applicationDao;

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
            chatMessageDao.deleteChat(chatRoomIdx);
            chatMessageDao.deleteChatPhoto(chatRoomIdx);
            postDao.modifyPostByChatRoomId(chatRoomIdx);
            applicationDao.modifyApplicationByChatRoomId(chatRoomIdx);
            return chatRoomDao.deleteChatRoom(chatRoomIdx);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
