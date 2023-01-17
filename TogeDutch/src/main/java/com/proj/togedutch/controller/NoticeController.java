package com.proj.togedutch.controller;


import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.service.ApplicationService;
//import com.proj.togedutch.service.NoticeService;
import com.proj.togedutch.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/notice")
/*public class NoticeController {

    @Autowired
    NoticeService noticeService;

    //공고생성
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Notice> applyNotice(@RequestBody Notice notice){
        try{
            Notice newNotice=noticeService.createNotice(notice);
            return new BaseResponse<>(newNotice);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
    // 공고 전체 조회 (최신순 / )
    @GetMapping("/")
    public BaseResponse<List<Notice>> getSortingNotice(@RequestParam String sort) throws BaseException {
        try{
            List<Notice> getNoticeRes = noticeService.getSortingNotice(sort);
            return new BaseResponse<>(getNoticeRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //특정 공지사항 조회
    @ResponseBody
    @GetMapping("/{noticeIdx}")
    public BaseResponse<Notice> getNotice(@PathVariable("noticeIdx") int noticeIdx) {
        try {
            User getUser = noticeService.getNotice(noticeIdx);
            return new BaseResponse<>(getNotice);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


        //공지사항 삭제

    //공지사항 수정 어랴움
    @ResponseBody
    @PatchMapping("/{noticeIdx}")
    public BaseResponse<Keyword> modifyKeyword(@PathVariable("noticeIdx") int noticeIdx, @RequestBody Notice notice) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            User user = userService.getUser(userIdx);
            keyword.setKeywordIdx(user.getKeywordIdx());
            Keyword modifyKeyword = userService.modifyKeyword(keyword);
            return new BaseResponse<>(modifyKeyword);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }






}*/
