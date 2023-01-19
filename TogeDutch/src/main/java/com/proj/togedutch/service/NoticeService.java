/*package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.dao.NoticeDao;
import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            notice.setNotice_id(noticeIdx);
            Notice newNotice = getNotice(noticeIdx);
            return newNotice;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

    }


    //TODO 공고 전체 조회
    public Notice getNotice(int noticeIdx) throws BaseException{
        try {
            Notice notice = noticeDao.getNotice(noticeIdx);
            return notice;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




    //공고 전체 조회 메소드(최신순)
    public List<Notice> getSortingNotice(String sort) throws BaseException{
        try{
            return noticeDao.getSortingNotice(sort);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //특정 공지사항 조회
    public User getNotice(int noticeIdx) throws BaseException{
        try {
            User user = noticeDao.getNotice(noticeIdx);
            return user;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //공지사항 수정


    //공지사항 삭제





}*/
