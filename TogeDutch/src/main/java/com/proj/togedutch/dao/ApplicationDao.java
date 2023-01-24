package com.proj.togedutch.dao;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

@Repository
public class ApplicationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostDao postdao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int applyPost(Application application,int userIdx){
        String createApplicationQuery="insert into Application(status,Post_post_id,User_user_id,ChatRoom_chatRoom_id,Post_User_user_id) VALUES(?,?,?,?,?)";
        Object[] createApplicationParams = new Object[]{ application.getStatus(), application.getPost_id(), application.getUser_id(), application.getChatRoom_id(),userIdx};
        this.jdbcTemplate.update(createApplicationQuery, createApplicationParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    //공고 수락
    @Transactional(rollbackFor=Exception.class)
    public Application modifyStatus(int applicationIdx) throws BaseException {
        String modifyStatusQuery="update Application set status =? where application_id= ?";
        Object[] modifyStatusParams = new Object[]{"수락완료",applicationIdx};
        if(this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams)==1)
            return getApplication(applicationIdx);
        else
            throw new BaseException(DATABASE_ERROR);
    }
    //공고 거절
    @Transactional(rollbackFor=Exception.class)
    public Application modifyStatus_deny(int applicationIdx) throws BaseException {
        String modifyStatusQuery="update Application set status =? where application_id= ?";
        Object[] modifyStatusParams = new Object[]{"수락거절",applicationIdx};
        if(this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams)==1)
            return getApplication(applicationIdx);
        else
            throw new BaseException(DATABASE_ERROR);
    }




    public Application getApplication(int applicationIdx){
        String getApplicationQuery = "select * from Application where application_id = ?";
        int getApplicationParams = applicationIdx;
        return this.jdbcTemplate.queryForObject(getApplicationQuery,
                (rs, rowNum) -> new Application(
                        rs.getInt("application_id"),
                        rs.getString("status"),
                        rs.getInt("Post_post_id"),
                        rs.getInt("User_user_id"),
                        rs.getInt("ChatRoom_chatRoom_id")),
                        getApplicationParams
        );
    }


    //신청 상태 전체 조회 (내가 업로드)
    public List<Application> getApplicationByUploadUserId(int userIdx) throws BaseException {
        String getApplicationQuery = "select * from Application where Application_user_id = ? ";

        return this.jdbcTemplate.query(getApplicationQuery,
                (rs, rowNum) -> new Application(
                        rs.getInt("application_id"),
                        rs.getString("status"),
                        rs.getInt("post_id"),
                        rs.getInt("user_id"),
                        rs.getInt("chatRoom_id")
                ), userIdx);
    }


    //신청 상태 전체 조회 (내가 참여한 공고)
    public List<Application> getApplicationByJoinUserId(int userIdx) throws BaseException {
        String getApplicationQuery = "select * From Application where post_id In( select Post_post_id from Post where User_user_id = ? )";

        return this.jdbcTemplate.query(getApplicationQuery,
                (rs, rowNum) -> new Application(
                        rs.getInt("application_id"),
                        rs.getString("status"),
                        rs.getInt("post_id"),
                        rs.getInt("user_id"),
                        rs.getInt("chatRoom_id")
                ), userIdx);
    }


    //내가 참여한 채팅방 전체조회
    public List<ChatRoom> getChatRoomByJoinUserId(int userIdx) throws BaseException {
        String getChatRoomQuery = "select * From ChatRoom where chatRoom_id In( select Application_chatRoom_id from Application where User_user_id = ? )";

        return this.jdbcTemplate.query(getChatRoomQuery,
                (rs, rowNum) -> new ChatRoom(
                        rs.getInt("chatRoom_id"),
                        rs.getTimestamp("created_at")
                ), userIdx);
    }

    //공고 상태 변경
    @Transactional(rollbackFor = Exception.class)
    public Post modifyPostStatus(int postIdx) throws BaseException {
        String modifyPostQuery = "update Post \n" + "set status = \"모집완료\"\n" + "where num_of_recruits = recruited_num and post_id=?";

        Object[] modifyPostParams = new Object[]{postIdx};
        if (this.jdbcTemplate.update(modifyPostQuery, modifyPostParams) == 1){
            Post modifyPost=postdao.getPostById(postIdx);
            return modifyPost;
        }
        else{
            throw new BaseException(DATABASE_ERROR);
        }
    }




}
