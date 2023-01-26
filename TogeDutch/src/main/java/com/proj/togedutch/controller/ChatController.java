package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.ChatMessage;
import com.proj.togedutch.entity.ChatPhoto;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/chat")
    public BaseResponse<List<ChatMessage>> getChatMessages(@PathVariable("chatRoom_id") int chatRoomId) throws BaseException {
        try{
            List<ChatMessage> chatMessages = chatMessageService.getChatMessages(chatRoomId);
            return new BaseResponse<>(chatMessages);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

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
}
