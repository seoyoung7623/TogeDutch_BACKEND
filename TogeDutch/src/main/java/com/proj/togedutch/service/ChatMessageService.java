package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.dao.ChatMessageDao;
import com.proj.togedutch.entity.ChatLocation;
import com.proj.togedutch.entity.ChatMeetTime;
import com.proj.togedutch.entity.ChatMessage;
import com.proj.togedutch.entity.ChatPhoto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.math.BigDecimal;
import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;


@Slf4j
@Service
public class ChatMessageService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatMessageDao chatMessageDao;

    @Autowired
    public ChatMessageService(ChatMessageDao chatMessageDao){
        this.chatMessageDao = chatMessageDao;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
    }

    public List<ChatMessage> findAllChatByRoomId(String roomId){
        return chatMessageDao.findAllChatByRoomId(roomId);
    }

    //채팅내역 전체조회
    public List<ChatMessage> getChatMessages (int chatRoom_id) throws BaseException {
        try {
            List<ChatMessage> chatMessages = chatMessageDao.getChatMessages(chatRoom_id);
            return chatMessages;
        }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ChatPhoto createChatPhoto(int chatRoomId,int user,String file) throws BaseException{
        try {
            int chatPhoto_id = chatMessageDao.createChatPhoto(chatRoomId,user,file);
            ChatPhoto chatPhoto = chatMessageDao.getChatPhoto(chatRoomId,chatPhoto_id);
            return chatPhoto;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ChatPhoto getChatPhoto(int chatRoomId, int chatPhotoId) throws BaseException{
        try{
            ChatPhoto getChatPhoto = chatMessageDao.getChatPhoto(chatRoomId,chatPhotoId);
            return getChatPhoto;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ChatMeetTime createChatMeetTime(int chatRoom_id, int user, String time) throws BaseException{
        try {
            int chatMeetTime_id = chatMessageDao.createChatMeetTime(chatRoom_id,user,time);
            ChatMeetTime chatMeetTime = chatMessageDao.getChatMeetTime(chatRoom_id,chatMeetTime_id);
            return chatMeetTime;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public String getImageUrl(int chatPhotoId) throws BaseException {
        try{
            return chatMessageDao.getImageUrl(chatPhotoId);
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 채팅방에 메시지 전송
//    public void sendChatMessage(ChatMessage chatMessage) {
//        // chatMessage.setUserCount(chatRoomRepository.getUserCount(chatMessage.getRoomId()));
//        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
//            //chatMessage.setContent(chatMessage.getSender() + "님이 방에 입장했습니다.");
//            chatMessage.setContent("[알림]");
//        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
//            chatMessage.setContent(chatMessage.getSender() + "님이 방에서 나갔습니다.");
//            chatMessage.setContent("[알림]");
//        }
//        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
//    }
/*
    // Post_id를 통해서 채팅방 가져오기 (미완)
    public int getChatRoomId(int post_id){
        return 1;
    }
*/
    // 채팅 메시지 삭제
    public int deleteChat(int chatRoomIdx) throws BaseException {
        try{
            int result = chatMessageDao.deleteChat(chatRoomIdx);
            return result;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 채팅 이미지 삭제
    public int deleteChatPhoto(int chatRoomIdx) throws BaseException {
        try{
            int result = chatMessageDao.deleteChatPhoto(chatRoomIdx);
            return result;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ChatLocation createChatLocation(int chatRoom_id, int user, BigDecimal latitude, BigDecimal longitude) throws BaseException {
        try {
            int chatLocationIdx = chatMessageDao.createChatLocation(chatRoom_id, user, latitude, longitude);
            ChatLocation chatLocation = chatMessageDao.getChatLocation(chatRoom_id,chatLocationIdx);
            return chatLocation;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ChatLocation getChatLocationById(int chatRoom_id, int chatLocationIdx) throws BaseException {
        try{
            ChatLocation getChatLocation = chatMessageDao.getChatLocation(chatRoom_id, chatLocationIdx);
            return getChatLocation;
        }catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ChatLocation putChatLocation(int chatRoom_id, int chatLocationIdx, BigDecimal latitude, BigDecimal longitude) throws BaseException {
        try {
            chatMessageDao.putChatLocation(chatRoom_id, chatLocationIdx, latitude, longitude);
            return getChatLocationById(chatRoom_id, chatLocationIdx);
        }catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
