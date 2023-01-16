package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.dao.ChatDao;
import com.proj.togedutch.dao.PostDao;
import com.proj.togedutch.entity.Chat;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatDao chatDao;
//    @PostConstruct
//    private void init() {
//        chatRoomMap = new LinkedHashMap<>();
//    }
//
//    public List<ChatRoom> findAllRoom() {
//        // 채팅방 생성순서 최근 순으로 반환
//        List chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
//    }
//
//    public ChatRoom findRoomById(String id) {
//        return chatRoomMap.get(id);
//    }
//
//    public ChatRoom createChatRoom(String name) {
//        ChatRoom chatRoom = ChatRoom.create(name);
//        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
//        return chatRoom;
//    }

    public Chat cheatemessage(Chat chat,int userIdx) throws BaseException {
        try{
            List<Chat> message;
            int chatIdx = chatDao.createMessage(chat,userIdx);
            Chat createChat = chatDao
            Post createPost = postDao.getPostById(postIdx);
            return new Chat()
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }
}
