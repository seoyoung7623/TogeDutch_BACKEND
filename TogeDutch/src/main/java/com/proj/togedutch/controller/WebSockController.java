package com.proj.togedutch.controller;

import com.proj.togedutch.dao.ChatDao;
import com.proj.togedutch.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebSockController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    //JwtService jwtService = new JwtService;
    ChatDao chatDao;

    @MessageMapping(value = "/chat/sendMessage")
    public void message(@Payload Message mes, SimpMessageHeaderAccessor headerAccessor){
        int user_id = 0;
        int chatroom_id = 0;
        try{
            // user_id = jwtService.getUserIdx();
            //jwt 회원 연결
        } catch (Exception e){
            user_id = mes.getUser_id();
            chatroom_id = mes.getChatRoom_id();
        }
        mes.setUser_id(user_id);
        Message recMessage = mes;
        List<Integer> revUser;

        switch (mes.getType()){
            case ENTER:
                recMessage = chatDao.insertMessage(mes);
                Message temp = recMessage;
                revUser =chatDao.getRevUser(user_id,chatroom_id);
                if (revUser.size() == 0){
                    messagingTemplate.convertAndSend("/topic/"+user_id,"{\"error\":\"Error conversation\"}");
                }
                revUser.forEach(u -> messagingTemplate.convertAndSend("/topic/"+u,temp));
                break;

            case TALK:

            case FILE:
        }
    }


}
