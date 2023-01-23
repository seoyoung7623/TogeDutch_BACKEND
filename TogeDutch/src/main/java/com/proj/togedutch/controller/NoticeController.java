package com.proj.togedutch.controller;


import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
//import com.proj.togedutch.service.NoticeService;
import com.proj.togedutch.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    NoticeService noticeService;

    // 공지사항 생성
    @PostMapping("")
    public BaseResponse<Notice> createNotice(@RequestBody Notice notice) throws BaseException{
        if(notice.getTitle() == null){
            return new BaseResponse<>(BaseResponseStatus.POST_NOTICE_EMPTY_TITLE);
        }
        if(notice.getContent() == null){
            return new BaseResponse<>(BaseResponseStatus.POST_NOTICE_EMPTY_CONTENT);
        }

        try {
            Notice newNotice = noticeService.createNotice(notice);
            return new BaseResponse<>(newNotice);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    
    // 특정 공지사항 조회
    @GetMapping("/{noticeIdx}")
    public BaseResponse<Notice> getNoticeById (@PathVariable("noticeIdx") int noticeIdx) throws BaseException {
        try {
            Notice findNotice = noticeService.getNoticeById(noticeIdx);
            return new BaseResponse<>(findNotice);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 전체 공지사항 조회 (최신순)
    @GetMapping("/")
    public BaseResponse<List<Notice>> getAllNotices(@RequestParam String sort){
        try{
            List<Notice> getNoticesRes = null;
            if(sort.equals("latest"))
                getNoticesRes = noticeService.getAllNotices(sort);
            return new BaseResponse<>(getNoticesRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/{noticeIdx}")
    public int deleteNotice(@PathVariable("noticeIdx") int noticeIdx) throws BaseException {
        try{
            int deleteNotice = noticeService.deleteNotice(noticeIdx);
            logger.info("Delete success");
            return deleteNotice;
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus()).getCode();
        }
    }

    // 공지사항 수정
    @PutMapping("/{noticeIdx}")
    public BaseResponse<Notice> modifyNotice(@PathVariable("noticeIdx") int noticeIdx, @RequestBody Notice notice) throws BaseException{
        if(notice.getTitle() == null){
            return new BaseResponse<>(BaseResponseStatus.POST_NOTICE_EMPTY_TITLE);
        }
        if(notice.getContent() == null){
            return new BaseResponse<>(BaseResponseStatus.POST_NOTICE_EMPTY_CONTENT);
        }

        try {
            Notice modifyNotice = noticeService.modifyNotice(noticeIdx, notice);
            return new BaseResponse<>(modifyNotice);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
