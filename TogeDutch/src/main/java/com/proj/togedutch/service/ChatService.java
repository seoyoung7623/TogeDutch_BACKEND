package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.dao.AdDao;
import com.proj.togedutch.dao.ChatDao;
import com.proj.togedutch.dao.PostDao;
import com.proj.togedutch.entity.Chat;
import com.proj.togedutch.entity.ChatMessage;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.repo.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
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




    /**
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage chatMessage) {
        // chatMessage.setUserCount(chatRoomRepository.getUserCount(chatMessage.getRoomId()));
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setContent(chatMessage.getSender() + "님이 방에 입장했습니다.");
            chatMessage.setContent("[알림]");
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setContent(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setS ender("[알림]");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }

    // Post_id를 통해서 채팅방 가져오기
    public int getChatRoomId(int post_id){

    }

}
