package com.proj.togedutch.controller;


import com.proj.togedutch.dao.ChatMessageDao;
import com.proj.togedutch.dao.ChatRoomDao;
import com.proj.togedutch.dto.ChatMessageDetailDto;
import com.proj.togedutch.dto.ChatMessageSaveDto;
import com.proj.togedutch.entity.ChatMessage;
import com.proj.togedutch.entity.ChatRoom;
import com.proj.togedutch.repo.ChatRoomRepository;
import com.proj.togedutch.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatWebSockController {
    // private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatMessageDao chatMessageDao;
    private final ChatRoomDao chatRoomDao;

    // 채팅 입장
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDetailDto message){
        String userName = chatMessageDao.userName(message.getUserId());
        message.setContent(userName + "님이 채팅방에 참여하였습니다.");

        //채팅리스트
        List<ChatMessageDetailDto> chatList = chatMessageService.findAllChatByRoomId(message.getRoomId());
        if(chatList != null) {
            for (ChatMessageDetailDto c : chatList) {
                message.setWriter(c.getWriter());
                message.setContent(c.getContent());
            }
        }

        simpMessagingTemplate.convertAndSend("/sub/chat/room/"+ message.getRoomId(),message);

//        ChatRoom chatRoom = chatRoomDao.findByRoomId(message.getRoomId());
//        ChatMessageSaveDto chatMessageSaveDto = new ChatMessageSaveDto(message.getRoomId(), message.getWriter(),message.getContent());
//        chatMessageDao.save(ChatMessage.toChatEntity(chatMessageSaveDto,chatRoom));
    }


    // 채팅 메시지
    //websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
    @MessageMapping("/chat/message")
    public void message(ChatMessageDetailDto message) {
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        System.out.println("연결성공");
        // ChatRoom과 연결
//        ChatRoom chatRoom= chatRoomDao.findByRoomId(message.getRoomId());
//        ChatMessageSaveDto chatMessageSaveDto = new ChatMessageSaveDto(message.getRoomId(),message.getWriter(), message.getContent());
//        ChatMessageDao.save(ChatMessage.toChatEntity(chatMessageSaveDto,chatRoom));
    }
}
