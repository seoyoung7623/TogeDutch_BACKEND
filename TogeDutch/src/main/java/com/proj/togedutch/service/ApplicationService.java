package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.dao.ApplicationDao;
import com.proj.togedutch.dao.PostDao;
import com.proj.togedutch.entity.Application;
import com.proj.togedutch.entity.ChatRoom;
import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proj.togedutch.utils.JwtService;

import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;
import static com.proj.togedutch.config.BaseResponseStatus.MODIFY_FAIL_USER;

@Service
public class ApplicationService {
    final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApplicationDao applicationDao;
    @Autowired
    JwtService jwtService;
    @Autowired
    PostDao postdao;

    //공고 신청
    public Application applyPost(int postIdx, Application application) throws BaseException {
        try {
            application.setPost_id(postIdx); // entity에있는 setter사용
            int userIdx = jwtService.getUserIdx();
            application.setUser_id(userIdx);
            Post newpost=postdao.getPostById(application.getPost_id());
            application.setStatus("수락대기");
            int applicationIdx = applicationDao.applyPost(application,newpost.getUser_id());

            Application createApplication = getApplication(applicationIdx);
            return createApplication;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }

    }

    //신청 수락
    public Application modifyStatus(int applicationIdx) throws BaseException{
        try{
            return applicationDao.modifyStatus(applicationIdx);
        }catch(Exception e){
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }


    //신청 거절
    public Application modifyStatus_deny(int applicationIdx) throws BaseException{
        try{
            return applicationDao.modifyStatus_deny(applicationIdx);
        }catch(Exception e){
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }



    //공고 전체 조회
    public Application getApplication(int postIdx) throws BaseException{
        try {
            Application application = applicationDao.getApplication(postIdx);
            return application;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //신청 상태 전체 조회 (내가 참여한 공고)
    public List<Application> getApplicationByJoinUserId(int userIdx) throws BaseException {
        try{
            List<Application> joinApplication = applicationDao.getApplicationByJoinUserId(userIdx);
            //System.out.print(UploadApplication);
            return joinApplication;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //신청 상태 전체 조회 (내가 업로드)
    public List<Application> getApplicationByUploadUserId(int userIdx) throws BaseException {
        try{
            List<Application> UploadApplication = applicationDao.getApplicationByUploadUserId(userIdx);
            return UploadApplication;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //채팅방 전체 조회 (내가 참여)
    public List<ChatRoom> getChatRoomByJoinUserId(int userIdx) throws BaseException {
        try{
            List<ChatRoom> joinChatRoom = applicationDao.getChatRoomByJoinUserId(userIdx);
            return joinChatRoom;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //공고 상태 변경
    public Post modifyPostStatusById(int postIdx) throws BaseException{
        try{
            Post modifyPostStatusById = applicationDao.modifyPostStatusById(postIdx);
            return modifyPostStatusById;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }



    // 채팅방 삭제 후 Application의 chatRoom_id로 null로 변경
    public int modifyApplicationByChatRoomId(int chatRoomIdx) throws BaseException {
        try{
            int result = applicationDao.modifyApplicationByChatRoomId(chatRoomIdx);
            return result;
        } catch(BaseException e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}