package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.ChatLocation;
import com.proj.togedutch.entity.ChatMeetTime;
import com.proj.togedutch.entity.ChatMessage;
import com.proj.togedutch.entity.ChatPhoto;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
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
    // 위도경도 위치 저장
    @PostMapping("/chatLocation")
    public BaseResponse<ChatLocation> postChatLocation(@PathVariable("chatRoom_id")int chatRoom_id, @RequestParam int user,
                                                       @RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude) {
        try {
            ChatLocation chatLocation = chatMessageService.createChatLocation(chatRoom_id, user, latitude, longitude);
            return new BaseResponse<>(chatLocation);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/chatLocation/{chatLocation_id}")
    public BaseResponse<ChatLocation> getChatLocation(@PathVariable("chatRoom_id")int chatRoom_id,
                                                      @PathVariable("chatLocation_id")int chatLocationIdx) {
        try {
            ChatLocation chatLocation = chatMessageService.getChatLocationById(chatRoom_id, chatLocationIdx);
            return new BaseResponse<>(chatLocation);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    // 위치 정보 수정
    @PutMapping("/chatLocation/{chatLocation_id}")
    public BaseResponse<ChatLocation> putChatLocation(@PathVariable("chatRoom_id")int chatRoom_id, @PathVariable("chatLocation_id")int chatLocationIdx,
                                                      @RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude) {
        try {
            ChatLocation chatLocation = chatMessageService.putChatLocation(chatRoom_id, chatLocationIdx, latitude, longitude);
            return new BaseResponse<>(chatLocation);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
