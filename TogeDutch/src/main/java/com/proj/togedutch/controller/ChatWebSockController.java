package com.proj.togedutch.controller;


import com.proj.togedutch.dao.ChatMessageDao;
import com.proj.togedutch.dao.ChatRoomDao;
import com.proj.togedutch.entity.ChatLocation;
import com.proj.togedutch.entity.ChatMeetTime;
import com.proj.togedutch.entity.ChatMessage;
import com.proj.togedutch.entity.ChatPhoto;
import com.proj.togedutch.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatWebSockController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatMessageDao chatMessageDao;
    private final ChatRoomDao chatRoomDao;

    // 채팅 입장
    //"/pub/chat/enter"
    @MessageMapping(value = "/enter")
    public void enter(ChatMessage message){
        String roomIdName = Integer.toString(message.getChatRoom_id());
        String userName = chatMessageDao.userName(message.getUserId());
        message.setContent(userName + "님이 채팅방에 참여하였습니다.");

        simpMessagingTemplate.convertAndSend("/sub/chat/room/"+ roomIdName,message);

        chatMessageDao.save(message);
    }

    // 채팅 퇴장
    //"/pub/chat/quit"
    @MessageMapping(value = "/quit")
    public void quit(ChatMessage message){
        String roomIdName = Integer.toString(message.getChatRoom_id());
        String userName = chatMessageDao.userName(message.getUserId());
        message.setContent(userName + "님이 채팅방에서 나갔습니다.");
        simpMessagingTemplate.convertAndSend("/sub/chat/room/"+ roomIdName,message);
        chatMessageDao.save(message);
    }


    // 채팅 메시지
    // websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
    @MessageMapping("/message")
    public void message(ChatMessage message) {
        String roomIdName = Integer.toString(message.getChatRoom_id());
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomIdName, message);
        chatMessageDao.save(message);
    }

    // 채팅 이미지 전송
    @MessageMapping("/image")
    public void image(ChatPhoto chatPhoto){
        String roomIdName = Integer.toString(chatPhoto.getChatRoom_id());
        simpMessagingTemplate.convertAndSend("sub/chat/room/"+roomIdName,chatPhoto);
        chatMessageDao.saveImg(chatPhoto);
    }

    // 채팅 위치 전송
    @MessageMapping("/Location")
    public void location(ChatLocation location){
        String roomIdName = Integer.toString(location.getChatRoomId());
        simpMessagingTemplate.convertAndSend("sub/chat/room/"+roomIdName,location);
        chatMessageDao.saveLocation(location);
    }

    //채팅 만남시간 전송
    @MessageMapping("/MeetTime")
    public void meetTime(ChatMeetTime meetTime){
        String roomIdName = Integer.toString(meetTime.getChatRoomId());
        simpMessagingTemplate.convertAndSend("sub/chat/room/"+roomIdName,meetTime);
        chatMessageDao.saveMeetTime(meetTime);
    }
}
