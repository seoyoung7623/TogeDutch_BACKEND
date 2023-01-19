/*package com.proj.togedutch.service;

import com.proj.togedutch.dao.NoticeDao;
import com.proj.togedutch.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NoticeDao noticeDao;


    공지사항 생성 메소드 미완성
    public Notice createNotice(Notice notice) throws BaseException {
        try {
            int noticeIdx = noticeDao.createNotice(notice);
            Keyword createKeyword = getNotice(noticeIdx);
            return createNotice;
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
