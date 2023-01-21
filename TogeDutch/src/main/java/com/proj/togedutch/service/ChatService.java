package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.dao.AdDao;
import com.proj.togedutch.dao.ChatDao;
import com.proj.togedutch.dao.PostDao;
import com.proj.togedutch.entity.Chat;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class ChatService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatDao chatDao;
    
    @Autowired
    public ChatService(ChatDao chatDao){
        this.chatDao = chatDao;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
    }


//    public Chat cheatemessage(Chat chat,int userIdx) throws BaseException {
//        try{
//            List<Chat> message;
//            int chatIdx = chatDao.createMessage(chat,userIdx);
//            Chat createChat = chatDao
//            Post createPost = postDao.getPostById(postIdx);
//            return new Chat()
//        }catch (Exception e){
//            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        }
//
//    }


}
