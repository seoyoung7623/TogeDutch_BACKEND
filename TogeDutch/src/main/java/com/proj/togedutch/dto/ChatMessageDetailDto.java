package com.proj.togedutch.dto;

import com.proj.togedutch.entity.ChatMessage;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDetailDto {
    private int chatId;
    private int chatRoomId;
    private int userId;
    private Timestamp createAt;

    private String roomId;
    private String writer;
    private String content;

    public ChatMessageDetailDto(int chat_id, int chatRoom_chatRoom_id, int user_user_id, Timestamp created_at, String content) {
        this.chatId = chat_id;
        this.chatRoomId = chatRoom_chatRoom_id;
        this.userId = user_user_id;
        this.createAt = created_at;
        this.content = content;
    }


//    public static ChatMessageDetailDto toChatMessageDetailDTO(ChatMessage chatMessage){
//        ChatMessageDetailDto chatMessageDetailDto = new ChatMessageDetailDto();
//
//        chatMessageDetailDto.setChatId(chatMessage.getId());
//
//        chatMessageDetailDto.setChatRoomId(chatMessage.getChatRoomEntity().getId());
//        chatMessageDetailDto.setRoomId(chatMessage.getChatRoomEntity().getRoomId());
//
//        chatMessageDetailDto.setWriter(chatMessage.getWriter());
//        chatMessageDetailDto.setMessage(chatMessageEntity.getMessage());
//
//        return chatMessageDetailDTO;
//
//    }
}
