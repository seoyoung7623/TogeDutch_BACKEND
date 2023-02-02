package com.proj.togedutch.controller;


import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Application;
import com.proj.togedutch.entity.ChatRoom;
import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.service.ApplicationService;
import com.proj.togedutch.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("")
public class ApplicationController {
    final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApplicationService applicationService;
    @Autowired
    PostService postService;

    // 공고 신청
    @ResponseBody
    @PostMapping("/post/{postIdx}/application")
    public BaseResponse<Application> applyPost(@PathVariable("postIdx") int postIdx){
        try{
            Application newApplication=applicationService.applyPost(postIdx);
            return new BaseResponse<>(newApplication);
        }catch(BaseException e){
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }


    //신청 수락
    @ResponseBody
    @PostMapping("/application/{applicationIdx}/accept")
    public BaseResponse<Application> modifyStatus(@PathVariable("applicationIdx") int applicationIdx){
        try{
            Application application=applicationService.modifyStatus(applicationIdx);
            return new BaseResponse<>(application);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    //신청 거절
    @ResponseBody
    @PostMapping("/application/{applicationIdx}/deny")
    public BaseResponse<Application> modifyStatus_deny(@PathVariable("applicationIdx") int applicationIdx){
        try{
            Application application=applicationService.modifyStatus_deny(applicationIdx);
            return new BaseResponse<>(application);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //신청 상태 전체 조회 (내가 참여한 공고)
    @ResponseBody
    @GetMapping("/user/{userIdx}/application/join")
    public BaseResponse<List<Application>> getApplicationByJoinUserId(@PathVariable("userIdx") int userIdx) throws BaseException {
        try {
            List<Application> getApplication = applicationService.getApplicationByJoinUserId(userIdx);
            return new BaseResponse<>(getApplication);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //신청 상태 전체 조회 (내가 업로드)
    @ResponseBody
    @GetMapping("/user/{userIdx}/application/upload")
    public BaseResponse<List<Application>> getApplicationBuUploadUserId(@PathVariable("userIdx") int userIdx) throws BaseException {
        try {
            List<Application> getApplication = applicationService.getApplicationByUploadUserId(userIdx);
            return new BaseResponse<>(getApplication);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }



    //채팅방 전체 조회 (내가 참여)
    @ResponseBody
    @GetMapping("/user/{userIdx}/chatroom")
    public BaseResponse<List<ChatRoom>> getChatRoomByJoinUserId(@PathVariable("userIdx") int userIdx) throws BaseException {
        try {
            List<ChatRoom> getChatRoom = applicationService.getChatRoomByJoinUserId(userIdx);
            return new BaseResponse<>(getChatRoom);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //공고 상태 변경
    @PutMapping("/application/post/status/{postIdx}")
    public BaseResponse<Post> modifyPostStatusById(@PathVariable("postIdx") int postIdx) throws BaseException{
        Post modifyPost=postService.getPostById(postIdx);

        if(modifyPost.getNum_of_recruits() == 0){
            return new BaseResponse<>(BaseResponseStatus.NUM_Of_RECRUITS_EMPTY);
        }
        if(modifyPost.getRecruited_num() == 0){
            return new BaseResponse<>(BaseResponseStatus.NUM_Of_RECRUITS_EMPTY);
        }

        try {
            Post modifyPostStatus = applicationService.modifyPostStatusById(postIdx);
            return new BaseResponse<>(modifyPostStatus);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}