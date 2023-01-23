package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.dao.NoticeDao;
import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class NoticeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NoticeDao noticeDao;


    //공지사항 생성
    public Notice createNotice(Notice notice) throws BaseException {
        try {
            int noticeIdx = noticeDao.createNotice(notice);
            Notice newNotice = noticeDao.getNoticeById(noticeIdx);
            return newNotice;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 공지사항 전체 조회 (최신순)
    public List<Notice> getAllNotices(String sort) throws BaseException{
        try {
            List<Notice> getAllNotices = noticeDao.getAllNotices(sort);
            return getAllNotices;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 공지사항 삭제
    public int deleteNotice(int noticeIdx) throws BaseException{
        try{
            int result = noticeDao.deleteNotice(noticeIdx);
            return result;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 공지사항 수정
    public Notice modifyNotice(int noticeIdx, Notice notice) throws BaseException{
        try{
            Notice modifyNotice = noticeDao.modifyNotice(noticeIdx, notice);
            return modifyNotice;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Notice getNoticeById(int noticeIdx) throws BaseException{
        try{
            Notice findNotice = noticeDao.getNoticeById(noticeIdx);
            return findNotice;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
