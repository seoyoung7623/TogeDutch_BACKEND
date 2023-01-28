package com.proj.togedutch.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.ChatMeetTime;
import com.proj.togedutch.entity.ChatMessage;
import com.proj.togedutch.entity.ChatPhoto;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.ChatMessageService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/chatRoom/{chatRoom_id}")
public class ChatController {
    @Autowired
    ChatMessageService chatMessageService;
    @Autowired
    AWSS3Service awsS3Service;
    @Value("${cloud.aws.url}")
    private String url;

    //채팅내용 가져오기
    @GetMapping("/chat")
    public BaseResponse<List<ChatMessage>> getChatMessages(@PathVariable("chatRoom_id") int chatRoomId) throws BaseException {
        try{
            List<ChatMessage> chatMessages = chatMessageService.getChatMessages(chatRoomId);
            return new BaseResponse<>(chatMessages);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 채팅이미지 생성
    @PostMapping("/chatPhoto")
    public BaseResponse<ChatPhoto> PostChatPhoto(@PathVariable("chatRoom_id") int chatRoomId,@RequestParam int user, @RequestPart MultipartFile file) throws IOException {
        try {
            String fileUrl = null;
            fileUrl = url + awsS3Service.uploadChatFile(file);
            ChatPhoto chatPhoto = chatMessageService.createChatPhoto(chatRoomId,user,fileUrl);
            return new BaseResponse<>(chatPhoto);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //채팅 이미지 가져오기
    @GetMapping("/chatPhoto/{chatPhoto_id}")
    public BaseResponse<ChatPhoto> GetChatPhoto(@PathVariable("chatRoom_id") int chatRoomId,@PathVariable("chatPhoto_id") int chatPhotoId) throws BaseException{
        try {
            ChatPhoto getChatPhoto = chatMessageService.getChatPhoto(chatRoomId,chatPhotoId);
            return new BaseResponse<>(getChatPhoto);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //만남시간 설정
    //RequestParam로 timestamp 받는방법?
    @PostMapping("/chatMeetTime")
    public BaseResponse<ChatMeetTime> postChatMeetTime(@PathVariable("chatRoom_id")int chatRoom_id, @RequestParam int user, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") String time) throws BaseException{
        try {
            ChatMeetTime chatMeetTime = chatMessageService.createChatMeetTime(chatRoom_id,user,time);
            return new BaseResponse<>(chatMeetTime);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
