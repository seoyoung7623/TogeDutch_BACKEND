package com.proj.togedutch.controller;


import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.Application;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.service.ApplicationService;
import com.proj.togedutch.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/post")
public class ApplicationController {
    final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApplicationService applicationService;


    //공고 신청
    @ResponseBody
    @PostMapping("/{postIdx}/application")
    public BaseResponse<Post> applyPost(@RequestBody Post post){
        try{
            Post newPost=ApplicationService.createPost(post);
            return new BaseResponse<>(newPost);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    //공고 수락
    @ResponseBody
    @PostMapping("/{postIdx}/application/accept")
    public BaseResponse<Post> acceptPost(@RequestBody Post post){
        try{
            Post acceptPost=ApplicationService.createPost(post);
            return new BaseResponse<>(acceptPost);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //공고 거절
    @ResponseBody
    @PostMapping("/{postIdx}/application/deny")
    public BaseResponse<Post> denyPost(@RequestBody Post post){
        try{
            Post denyPost=ApplicationService.createPost(post);
            return new BaseResponse<>(denyPost);
        }catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    //공고 상태 전체 조회(내가 업로드)
    @ResponseBody
    @GetMapping("{postIdx}/application/upload")
    public BaseResponse<Post> getPost(@PathVariable("{postIdx}/application/upload") int postIdx) {
        try{
            Post getPost=postService.getPost(postIdx);
            return new BaseResponse<>(getPost);
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //공고 상태 잔체 조회(내가 참여한 공고)
    @ResponseBody
    @GetMapping("{postIdx}/application/join")
    public BaseResponse<Post> getJoinPost(@PathVariable("{postIdx}/application/join") int postIdx) {
        try{
            Post getJoinPost=postService.getJoinPostBypostIdx(postIdx);
            return new BaseResponse<>(getJoinPost);
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //채팅방 전체 조회(내가 참여)
    @ResponseBody
    @GetMapping("{userIdx}/chatroom")
    public BaseResponse<User> getJoinPost(@PathVariable("{userIdx}/chatroom") int userIdx) {
        try{
            User getChatroom=userService.getChatByuserIdx(userIdx);
            return new BaseResponse<>(getChatroom);
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}