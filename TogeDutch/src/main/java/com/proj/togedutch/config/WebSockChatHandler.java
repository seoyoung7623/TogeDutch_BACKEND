package com.proj.togedutch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proj.togedutch.entity.ChatRoom;
import com.proj.togedutch.entity.Message;
import com.proj.togedutch.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.WebSocketNamespaceHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ChatService chatService;
    private final ObjectMapper objectMapper;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.info("payload {}", payload);
        Message chatMessage = objectMapper.readValue(payload, Message.class);

        // ChatRoom chatRoom = chatService.findRoomById(chatMessage,getRoomId()); //findRoomById 추가해야됨
        // ChatRoom.handlerActions(session,chatMessage,chatService);

        TextMessage textMessage = new TextMessage("Welcome chatting Server!");
        session.sendMessage(textMessage);
    }
}
