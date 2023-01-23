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
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int applyPost(Application application){
        String createApplicationQuery="insert into Application(status,post_id,user_id,chatRoom_id) VALUES(?,?,?,?,?)";
        Object[] createApplicationParams = new Object[]{ application.getStatus(), application.getPost_id(), application.getUser_id(), application.getChatRoom_id()};
        this.jdbcTemplate.update(createApplicationQuery, createApplicationParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    //공고 수락
    @Transactional(rollbackFor=Exception.class)
    public Application modifyStatus(int applicationIdx) throws BaseException {
        String modifyStatusQuery="update Application set status =? where applicationIdx= ?";
        Object[] modifyStatusParams = new Object[]{applicationIdx};
        if(this.jdbcTemplate.update(modifyStatusQuery,modifyStatusParams)==1)
            return getApplication(applicationIdx);
        else
            throw new BaseException(DATABASE_ERROR);
    }
    //공고 거절
    @Transactional(rollbackFor=Exception.class)
    public Application modifyStatus_deny(int applicationIdx) throws BaseException {
        String modifyStatusQuery="update Application set status =? where applicationIdx= ?";
        Object[] modifyStatusParams = new Object[]{applicationIdx};
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
                        rs.getInt("post_id"),
                        rs.getInt("user_id"),
                        rs.getInt("chatRoom_id")),
                        getApplicationParams //컬럼을 다 써주는 이유가 있는가?
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
                        rs.getInt("chatRoomIdx"),
                        rs.getTimestamp("created_at")
                ), userIdx);
    }

    //공고 상태 변경
    @Transactional(rollbackFor = Exception.class)
    public Post modifyPostStatus(int applicationIdx, Post post) throws BaseException {
        String modifyPostQuery = "update Post , set title = ?,url=?,delivery_tips=?,minimum=?,order_time=?,num_of_recruits=?,recruited_num=?,status=?,created_at=?,updated_at=?,user_id=?,image=?,latitude=?,longitude=?,where post.num_of_recruits=? == post.recruited_num=?";

        Object[] modifyPostParams = new Object[]{post.getTitle(), post.getUrl(), post.getDelivery_tips(), post.getMinimum(), post.getOrder_time(), post.getNum_of_recruits(),post.getRecruited_num(),post.getStatus(),post.getCreated_at(),post.getUser_id(),post.getImage(),post.getLatitude(),post.getLongitude(),post.getNum_of_recruits(),post.getRecruited_num()};
        if (this.jdbcTemplate.update(modifyPostQuery, modifyPostParams) == 1){
            post.setStatus("모집완료");
            return post;}
        else{
            throw new BaseException(DATABASE_ERROR);
        }
    }






    /*
    public List<Application> getApplicationStatus(int userIdx) {
        List<Post> getPostRes;

        String getquery = "select * where post_user_id = ?";
        Object[] modifyStatusParams = new Object[]{userIdx};
        int getuserParams = userIdx;
        return this.jdbcTemplate.query(getquery,userIdx
                (rs.rowNum) -> new Post(
                        rs.getInt("post_id"),
                        rs.getString("title"),
                        rs.getString("url"),
                        rs.getInt("delivery_tips"),
                        rs.getInt("minimum"),
                        rs.getTimestamp("order_time"),
                        rs.getInt("num_of_recruits"),
                        rs.getInt("recruited_num"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        getuserParams

                ));
        // userIdx랑 Post의 user_id가 일치하는 Post 객체들을 다 담는 것
    }*/   //getPostres. 하나씩 PostId 추출 -> Application post_id 인거 Application 추출
}
